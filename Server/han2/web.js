function load() {
    if (GBrowserIsCompatible()) {

      // Get map (Version 2)
      var map = new GMap2(document.getElementById("map"));
      map.setUIToDefault(); // Default user interface

      // Get course
      GDownloadUrl("output_xml.php", function(data) {
        var xml = GXml.parse(data);
        var markers = 
        xml.documentElement.getElementsByTagName("marker");
        var points = new Array(0); // For polyline
        // Loop through the markers
        for (var i = 0; i < markers.length; i++) {
          var datetime = markers[i].getAttribute("datetime");
          var point = 
          new GLatLng(parseFloat(markers[i].getAttribute("lat")),
          parseFloat(markers[i].getAttribute("lng")));
          points[i] = 
          new GLatLng(parseFloat(markers[i].getAttribute("lat")),
          parseFloat(markers[i].getAttribute("lng")));
          var marker = createMarker(point, datetime);
          map.addOverlay(marker);
        } // End loop
        // Polyline
        var polyline = new GPolyline(points, "#ff0000", 4);
        map.addOverlay(polyline);
        // Set map centre (to last point), zoom level
        map.setCenter(point, 9);
        // InfoWindow HTML (last marker)
        var html = "";
        html += "<div id=\"infobox\">";
        html += "Hello. This is my latest position on";
        html += "<br />" + datetime;
        html += "<br />updated manually with my mobile.";
        html += "</div>";
        map.openInfoWindowHtml(point, html);
      });
    }
  }

  // General markers
  function createMarker(point, datetime) {
    var marker = new GMarker(point, datetime);
    var html = "<div id=\"infobox\">Co-ords: " + point + "<br />" 
    + datetime + "<br /><a href=\"/map\">Re-load</a></div>";
    GEvent.addListener(marker, 'click', function() {
      marker.openInfoWindowHtml(html);
    });
    return marker;
  }