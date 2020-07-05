<?php
ini_set('precision',17);
require_once "core.php";
class LatLng {
	private $lat;
	private $lng;
	
	function __construct($lat, $lng){
		$this->lat = $lat;
		$this->lng = $lng;
	}

	public function getLat(){
		return $this->lat;
	}

	public function getLng(){
		return $this->lng;
	}
}

class MarkerResult {
	private $id;
	private $index;
	private $marker;

	function __construct($id, $index, Marker $marker){
		$this->id = $id;
		$this->index = $index;
		$this->marker = $marker;
	}

	public function getId(){
		return $this->id;
	}

	public function getIndex(){
		return $this->index;
	}

	public function getMarker(){
		return $this->marker;
	}
}

class Graph {
	private $marker;
	private $markerIdCollection;
	private $road;

	function __construct($jsonGraph){
		$temp = json_decode($jsonGraph, false, 512, JSON_BIGINT_AS_STRING);

		$this->markerIdCollection = [];
		$this->marker = [];
		$this->road = [];

		$counter = 0;
		foreach ($temp->Markers->marker as $key => $value) {
			$this->markerIdCollection[$this->getUniqueId($value->coordinate)] = $counter;
			$this->marker[$counter] = new Marker(new LatLng(
				$value->coordinate->lat,
				$value->coordinate->lng
			));
			$counter++;
		}
		
		foreach ($temp->Markers->roads as $key => $value) {
			/*$this->road[$key] = new Road(
				$this->markerIdCollection[$this->getUniqueId($value->path[0])],
				$this->markerIdCollection[$this->getUniqueId($value->path[1])]
			);*/
			$this->marker[$this->markerIdCollection[$this->getUniqueId($value->path[0])]]->addLink(
				$this->markerIdCollection[$this->getUniqueId($value->path[1])], 
				$key
			);
			$this->marker[$this->markerIdCollection[$this->getUniqueId($value->path[1])]]->addLink(
				$this->markerIdCollection[$this->getUniqueId($value->path[0])], 
				$key
			);
		}
	}

	public function getNearbyMarker(LatLng $position){
		if (count($this->markerIdCollection) == 0)
			return null;

		$shortest = null;
		$temp;
		$selectedid;
		foreach ($this->markerIdCollection as $key => $value) {
			$temp = Math::Haversine($position, $this->marker[$value]->getCoordinate());
			if (is_null($shortest) || $temp < $shortest){
				$shortest = $temp;
				$selectedid = $key;
			}
		}
		return new MarkerResult($selectedid, $this->markerIdCollection[$selectedid], $this->marker[$this->markerIdCollection[$selectedid]]);
	}

	private function getMarkerByIndex($index){
		if (Validation::keyExists($index, $this->marker) && Validation::Check($this->marker[$index])){
			return $this->marker[$index];
		}
		throw new Exception("Index '$index' is undefined", 1);
	}

	public function getDistance(MarkerResult $marker, $linkIndex){
		if (Validation::keyExists($linkIndex, $marker->getMarker()->getLink()) && Validation::Check($marker->getMarker()->getLink()[$linkIndex])){
			return Math::Haversine(
				$marker->getMarker()->getCoordinate(),
				$this->marker[$linkIndex]->getCoordinate()
				);
		}
		throw new Exception("Link index '$linkIndex' is undefined", 1);
	}

	public function getMarker($id){
		if (Validation::keyExists($id, $this->markerIdCollection) && Validation::Check($this->markerIdCollection[$id])){
			return new MarkerResult(
				$id, 
				$this->markerIdCollection[$id], 
				$this->getMarkerByIndex($this->markerIdCollection[$id])
			);
		}
		throw new Exception("Id '$id' is undefined", 1);	
	}

	public function getMarkerByLink(MarkerResult $marker, $linkIndex){
		if (Validation::keyExists($linkIndex, $marker->getMarker()->getLink()) && Validation::Check($marker->getMarker()->getLink()[$linkIndex])){
			return $this->getMarker(array_search($linkIndex, $this->markerIdCollection));
		}
		throw new Exception("Link index '$linkIndex' is undefined", 1);
	}

	// public function getRoad($id){
	// 	if (Validation::Check($this->road[$id])){
	// 		return $this->getMarkerByIndex($this->road[$id]);
	// 	}
	// 	throw new Exception("Id '$id' is undefined", 1);
	// }

	private function getUniqueId($coordinate){
		if (Validation::Check($coordinate->lat)){
			if(Validation::Check($coordinate->lng)){
				return $coordinate->lat . "_" . $coordinate->lng;
			}
		}
		throw new Exception(Validation::getMessage());
	}
}

class Marker {
	private $coordinate;
	private $link;

	function __construct(Latlng $coordinate){
		$this->coordinate = $coordinate;
		$this->link = [];
	}

	public function addLink($key, $pathId){
		$this->link[$key] = new Link($pathId);
	}

	public function getCoordinate(){
		return $this->coordinate;
	}

	public function getLink(){
		return $this->link;
	}
}

class Link {
	private $pathId;

	function __construct($pathId){
		$this->pathId = $pathId;
	}

	public function getPathId(){
		return $this->pathId;
	}
}

class Road {
	private $path;

	function __construct($startMarkerKey, $endMarkerKey){
		$this->path = [$startMarkerKey, $endMarkerKey];
	}

	public function getPath(){
		return $this->path;
	}
}