  var wsStatus = 'NOT CONNECTED';
    var reportCount = 0;
    var foodIcons = ["cherry", "banana", "apple", "watermelon", "pizza"];
    var iconBase = '';
    var myLatlng;
    var mapOptions;
    var map;
    var foodReports = 0;
    
    $( document ).ready(function() {
      myLatlng = new google.maps.LatLng(centerLat,  centerLon );
      mapOptions = {
        zoom: 10,
        center: myLatlng
      }
      map = new google.maps.Map(document.getElementById('map-canvas'), mapOptions);
      WebSocketTest();
      var now = new Date();
      $("#startTime").html("start time: " + (now.getMonth() +1 )+ "-" + now.getDate() + "-" + now.getYear() + " "+now.getHours() + ":" + now.getMinutes() + ":" + now.getSeconds())  ;
    });
  
  function getRandomColor() {
    var letters = '0123456789ABCDEF'.split('');
    var color = '';
    for (var i = 0; i < 6; i++ ) {
        color += letters[Math.floor(Math.random() * 16)];
    }
    return color;
  }
    function updateWsStatus() { 
      $("#ws-status").html("websocket status:         " + wsStatus);
    }

    function WebSocketTest() {
      if ("WebSocket" in window){
          updateWsStatus();

          var ws 
          try {  
            //TODO: fix URL 
            ws = new WebSocket(wsURL + wsEndPoint);
          } catch(e) {
            wsStatus="NOT CONNECTED";
            updateWsStatus();
          }
          ws.onopen = function()
          {
            // Web Socket is connected, send data using send()
            wsStatus="CONNECTED";
            updateWsStatus();
          };
          ws.onmessage = function (evt)
          {
            var received_msg = JSON.parse(evt.data);
   
            var markerLocation = new google.maps.LatLng(received_msg.lat, received_msg.lon);
            var pinImage;
            var pcNum = Math.floor((Math.random() * 10) + 1); 
            console.log(pcNum);
            if(pcNum % 3 == 0){
              pinImage = new google.maps.MarkerImage(baseURL + "hungry.png");
            }else{
              foodReports++;
              var pinColor = getRandomColor();
              var foodNum = Math.floor((Math.random() * 10) + 1); 
              foodIcons
                  pinImage = new google.maps.MarkerImage( baseURL+ foodIcons[foodNum % 5] + ".png");

            }

            var marker = new google.maps.Marker({
              position: markerLocation,
              map: map,
              animation: google.maps.Animation.DROP,
              icon: pinImage,
              title: received_msg.qty
            });
            google.maps.event.addListener(marker, 'click', function() {

                $("#markerDetail").html("location: some readable address " + marker.getPosition());
                if(marker.getIcon().url.indexOf("hungry.png") == -1){
                  $("#markerDetail").html($("#markerDetail").html() + "<br/>type: food <br/>food for ~ number of people: " + marker.getTitle() );
                }else{
                  $("#markerDetail").html($("#markerDetail").html() + "<br/> type: potential consumer");
                }

            });
            reportCount++;
            $("#reportCount").html("reported: " + reportCount + "( food# " + foodReports + ", potential consumer# "+ (reportCount - foodReports) + ")" );
            $("#resolvedCount").html("resolved: " + parseInt(reportCount * 5.0/ 100.0));
          setTimeout(function () {
                  marker.setMap(null);
          }, markerTimeout);
          ws.onclose = function()
          {
            // websocket is closed.
            wsStatus="DISCONNECTED";
            updateWsStatus();        
          };
        }
        }else
          {
            wsStatus="NOT SUPPORTED";
            updateWsStatus(); 
          }
        }

  function initialize() {
  }

  google.maps.event.addDomListener(window, 'load', initialize);
