function initMap() {
    var mapDiv = document.getElementById('map');
    map_cls = new Map(mapDiv, 
    	new StartMaps(15, 
    		new Coordinate(-2.279512106379137, 118.55831623077393), 
    		google.maps.MapTypeId.ROADMAP));
}

function _ssH (fb){
	global_answer = JSON.parse(fb); 
	map_cls.Markers.initMarker();
	g_maps.setZoom(global_answer.properties.zoom);
	g_maps.setCenter(global_answer.properties.center);
}

function _eeH (){
    alert("1");
}

function UniqueID(latLng){
	try{
		return latLng.lat() + "_" + latLng.lng();
	}catch(err){
		return latLng.lat + "_" + latLng.lng;
	}
}

function ChangeKey(object, old_key, new_key){
	object[new_key] = object[old_key];
	delete object[old_key];
}
class StartMaps{

	constructor(zoomnumber, coordinate, mapType){
		this.zoom = zoomnumber;
		this.center = coordinate;
		this.mapTypeId = mapType;
	}
}
class Coordinate{
	constructor(latitude, longitude){
		this.lat = latitude;
		this.lng = longitude;
	}
}
class Map {
  
  constructor(mapDiv, center) {
    g_maps = new google.maps.Map(mapDiv, center);
    this.properties = center;
    this.Markers = new MarkerCollection();
    global_map_allowAddNote = false;
    this.addEventHandler();
    this.loadData();
  }

  loadData(){
  		sender(
        "ajaxservice.php", 
        "post", 
        "aksi=loadmaps", 
        "json", 
        _ssH, _eeH
    );
  }

  addEventHandler(){
  	g_maps.addListener('zoom_changed', function(){
    	map_cls.properties.zoom = g_maps.getZoom();
    });

    g_maps.addListener('center_changed', function(){
    	map_cls.properties.center = g_maps.getCenter();
    });

    g_maps.addListener('click', function(event){
    	if (global_map_allowAddNote){
	    	map_cls.Markers.Add(
	    		new Coordinate(
	    			event.latLng.lat(), 
	    			event.latLng.lng()), 
	    		title_temp, 
	    		g_maps
	    	);
    	}else{
    		map_cls.Markers.setAnimation(global_activemarker);
    	}
	});

	g_maps.addListener('rightclick', function(event){
		global_map_allowAddNote = !global_map_allowAddNote;
	});
  }
}
class MarkerCollection{

	constructor(){
		this.marker = {};
		this.roads = {};

		this.count = 0;
		this.totcount = 0;
		this.roadcount = 0;

		global_markers = {};
		global_polyline = {};
		global_activemarker = null;
		global_startdragposition = null;
	}

	Add(location, title){
		this.AddMarker(location, title, this.totcount);
		this.count++;
		this.totcount++;
	}

	initMarker(){
		for (tempkey in global_answer.Markers.marker){
			this.AddMarker(
				global_answer.Markers.marker[tempkey].coordinate,
				global_answer.Markers.marker[tempkey].title,
				global_answer.Markers.marker[tempkey].index
				);
			map_cls.Markers.marker[tempkey].linkedTo = global_answer.Markers.marker[tempkey].linkedTo;
			map_cls.Markers.marker[tempkey].linkCount = global_answer.Markers.marker[tempkey].linkCount;
		}

		for (tempkey in global_answer.Markers.roads){
			temparr = [
				new google.maps.LatLng(
					global_answer.Markers.roads[tempkey].path[0].lat,
					global_answer.Markers.roads[tempkey].path[0].lng
					),
				new google.maps.LatLng(
					global_answer.Markers.roads[tempkey].path[1].lat,
					global_answer.Markers.roads[tempkey].path[1].lng
					)
			];

			global_polyline[tempkey] = new google.maps.Polyline({
					path: temparr,
					editable: false,
					strokeColor: '#000000',
					strokeOpacity: 0.5,
					strokeWeight: 2,
					map: g_maps
				})

			map_cls.Markers.roads[tempkey] = new Road(tempkey);
		}

		this.count = global_answer.Markers.count;
		this.totcount = global_answer.Markers.totcount;
		this.roadcount = global_answer.Markers.roadcount;
	}

	AddMarker(location, title, index){
		global_curindex = UniqueID(location)
		global_markers[global_curindex] = new google.maps.Marker({
        	position: location,
        	map: g_maps,
        	draggable: true,
        	title: title,
        	animation: null,
        	visible: false
      	});
		this.AddClickHandler();
		this.AddDragStartHandler();
		this.AddDragEndHandler();
		this.AddRightClickHandler();
		map_cls.Markers.marker[global_curindex] = new Marker(
			global_curindex, 
			index,
			global_markers[global_curindex].getTitle()
		);
		global_markers[global_curindex].setVisible(true);
	}

	AddClickHandler(){
		global_markers[global_curindex].addListener('click', function(event){
			map_cls.Markers.setAnimation(UniqueID(event.latLng));
		});
	}

	AddDragStartHandler(){
		global_markers[global_curindex].addListener('dragstart', function(event){
			global_startdragposition = UniqueID(event.latLng);
		});
	}

	AddDragEndHandler(){
		global_markers[global_curindex].addListener('dragend', function(event){
			global_curindex = UniqueID(event.latLng);
			map_cls.Markers.marker[global_startdragposition].changeID(global_curindex);
			ChangeKey(map_cls.Markers.marker, global_startdragposition, global_curindex);
			if (global_activemarker === global_startdragposition){
				global_activemarker = global_curindex;
			}
			for (tempkey in map_cls.Markers.marker[global_curindex].linkedTo){
				temppaths = map_cls.Markers.roads[map_cls.Markers.marker[global_curindex].linkedTo[tempkey].path];
				temppaths.setPoint(
					map_cls.Markers.marker[global_curindex].linkedTo[tempkey].order,
					event.latLng
				);
				templinked = map_cls.Markers.marker[tempkey].linkedTo[global_startdragposition];
				map_cls.Markers.marker[tempkey].addLink(
					global_curindex, 
					templinked.path,
					templinked.distance,
					templinked.order);
				map_cls.Markers.marker[tempkey].removeLinkAt(global_startdragposition);
			}
		});
	}

	AddRightClickHandler(){
		global_markers[global_curindex].addListener('rightclick', function(event){

			map_cls.Markers.RemoveAt(UniqueID(event.latLng));
		});

	}

	makePolyline(index){
		point1 = map_cls.Markers.marker[global_activemarker];
		point2 = map_cls.Markers.marker[index];
		if (point1.index < point2.index){
			tempstr = point1.index + "_" + point2.index;
			temparr = [point1.coordinate, point2.coordinate];
			tempint = [0,1];
		}else{
			tempstr = point2.index + "_" + point1.index;
			temparr = [point2.coordinate, point1.coordinate];
			tempint = [1,0];
		}
		if (map_cls.Markers.roads[tempstr] == undefined){

			global_polyline[tempstr] = new google.maps.Polyline({
					path: temparr,
					editable: false,
					strokeColor: '#000000',
					strokeOpacity: 0.5,
					strokeWeight: 2,
					map: g_maps
				})

			map_cls.Markers.roads[tempstr] = new Road(tempstr);

			point1.addLink(index, tempstr, 0, tempint[0]);
			point2.addLink(global_activemarker, tempstr, 0, tempint[1]);
			map_cls.Markers.roadcount++;
		}
	}

	setAnimation(index){
		if (global_activemarker !== null){
			map_cls.Markers.marker[global_activemarker].toggleBounce();
		}
		if (global_activemarker === index){
			global_activemarker = null;
		}else{
			map_cls.Markers.marker[index].toggleBounce();
			
			if (global_activemarker != null){
				map_cls.Markers.makePolyline(index);
			}
			global_activemarker = index;
		}
	}

	RemoveAt(index){
		if (map_cls.Markers.marker[index] !== undefined){
			for (tempkey in map_cls.Markers.marker[index].linkedTo){
				map_cls.Markers.marker[tempkey].removeLinkAt(index);
				this.RemoveRoadAt(map_cls.Markers.marker[index].linkedTo[tempkey].path);
			}

			if (global_activemarker === index){
				global_activemarker = null;
			}

			map_cls.Markers.marker[index].Remove();
			delete map_cls.Markers.marker[index];
			map_cls.Markers.count--;
		}
	}

	RemoveRoadAt(index){
		map_cls.Markers.roads[index].Remove();
		delete map_cls.Markers.roads[index];
		map_cls.Markers.roadcount--;
	}
}
class Road{

	constructor(id){
		this.id = id;
		this.path = global_polyline[this.id].getPath().getArray();
	}

	setPoint(index, value){
		if (index === 0){
			this.path[0] = value;
		}else{
			this.path[this.path.length - 1] = value;
		}
		
		global_polyline[this.id].setPath(this.path);
	}

	Remove(){
		global_polyline[this.id].setMap(null);
		delete global_polyline[this.id];
	}
}
class Marker{

	constructor(id, index, title){
		this.id = id;
		this.index = index;
		this.title = title;
		this.linkedTo = {};
		this.linkCount = 0;
		this.coordinate = global_markers[this.id].getPosition();
	}

	changeID(newID){
		ChangeKey(global_markers, this.id, newID);
		this.id = newID;
		this.coordinate = global_markers[this.id].getPosition();
	}

	changeTitle(newTitle){
		this.title = newTitle;
		global_markers[this.id].setTitle(this.title);
	}

	addLink(index, path, distance, order){
		this.linkedTo[index] = {path: path, distance: distance, order: order};
		this.linkCount++;
	}

	removeLinkAt(index){
		if (this.linkedTo[index]!= undefined){
			delete this.linkedTo[index];
			this.linkCount--;
		}
	}

	toggleBounce() {
	  	if (global_markers[this.id].getAnimation() !== null) {
	    	global_markers[this.id].setAnimation(null);
	  	} else {
	    	global_markers[this.id].setAnimation(google.maps.Animation.BOUNCE);
	  	}
	}

	Remove(){
		global_markers[this.id].setMap(null);
		delete global_markers[this.id];
	}
}