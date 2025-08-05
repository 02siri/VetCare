let autocomplete;
let addressField;
let map;
let markers = [];

function initMap() {
    addressField = document.getElementById("address");
    autocomplete = new google.maps.places.Autocomplete(addressField, {
        componentRestrictions: { country: ["au"] },
        fields: ["address_components", "geometry"],
        types: ["address"],
    });
    autocomplete.addListener("place_changed", fillInAddress);

    if (!userLocation) return;

    map = new google.maps.Map(document.getElementById('map'), {
        zoom: 12,
        center: userLocation
    });

    addMarkersToMap();
}

function addMarkersToMap() {
    if (!map) return;

    // Clear existing markers
    markers.forEach(marker => marker.setMap(null));
    markers = [];

    var bounds = new google.maps.LatLngBounds();

    // Marker for the user's location
    var userMarker = new google.maps.Marker({
        position: userLocation,
        map: map,
        title: "Your Location",
        icon: 'http://maps.google.com/mapfiles/ms/icons/blue-dot.png'
    });
    bounds.extend(userMarker.getPosition());
    markers.push(userMarker);

    // Markers for each clinic
    clinics.forEach(function(clinic, index) {
        var marker = new google.maps.Marker({
            position: {lat: clinic.latitude, lng: clinic.longitude},
            map: map,
            title: clinic.name,
            label: (index + 1).toString()
        });

        bounds.extend(marker.getPosition());
        markers.push(marker);

        var infoWindow = new google.maps.InfoWindow({
            content: '<h3>' + clinic.name + '</h3>' +
                     '<p>' + clinic.address + '</p>' +
                     '<p>Distance: ' + clinic.distance.toFixed(2) + ' km</p>'
        });

        marker.addListener('click', function() {
            infoWindow.open(map, marker);
        });
    });

    map.fitBounds(bounds);
}

function fillInAddress() {
    const place = autocomplete.getPlace();
    let postcode = "";

    for (const component of place.address_components) {
        const componentType = component.types[0];
        if (componentType === "postal_code") {
            postcode = component.long_name;
            break;
        }
    }

    document.getElementById("postcode").value = postcode;
}

function handleMapError() {
    console.error('Google Maps failed to load');
    document.getElementById('map').innerHTML = '<p>Failed to load Google Maps. Please check your internet connection and try again.</p>';
}

function loadMoreClinics() {
    if (!nextPageToken) return;

    fetch(`/load-more-clinics?address=${encodeURIComponent(address)}&pageToken=${nextPageToken}`)
        .then(response => response.json())
        .then(data => {
            if (data.error) {
                console.error(data.error);
                return;
            }

            clinics = clinics.concat(data.clinics);
            nextPageToken = data.nextPageToken;

            // Update the clinics list in the UI
            const clinicsList = document.getElementById('clinics-list');
            data.clinics.forEach((clinic, index) => {
                const li = document.createElement('li');
                li.className = 'clinic-item';
                li.innerHTML = `
                    <h3>${clinics.length + index + 1}. ${clinic.name}</h3>
                    <p>${clinic.address}</p>
                    <p>Distance: ${clinic.distance.toFixed(2)} km</p>
                `;
                clinicsList.appendChild(li);
            });

            addMarkersToMap();
        })
        .catch(error => console.error('Error:', error));
}