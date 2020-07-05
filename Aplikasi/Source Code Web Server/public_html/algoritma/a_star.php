<?php
require_once "graph.php";

class PathResult {
	public $path;
	public $distance;

	function __construct($distance){
		$this->path = [];
		$this->distance = $distance;
	}

	public function Add(LatLng $latlng){
		$this->path[] = new Path ($latlng->getLat(), $latlng->getLng());
	}
}

class Path{
	public $lat;
	public $lng;

	function __construct($lat, $lng){
		$this->lat = $lat;
		$this->lng = $lng;
	}
}

class A_Star{
	private $start;
	private $destination;
	private $startmarker;
	private $destinationmarker;
	
	private $graph;
	private $closelist;			//jalan yang dilalui
	private $finish;			//jika ketemu
	private $traveled;			//total jarak
	private $traversedmarker;	

	private $tempmarker;

	function __construct(Graph $graph){
		$this->graph = $graph;
	}

	public function getPaths(LatLng $start, LatLng $destination){
		$this->start = $start;
		$this->destination = $destination;
		$this->startmarker = $this->graph->getNearbyMarker($this->start);
		$this->destinationmarker = $this->graph->getNearbyMarker($this->destination);
		$this->finish = false;
		$this->traveled = 0;
		$this->closelist = [];
		$this->traversedmarker = [];
		//var_dump($this->startmarker);						//Debuging
		//var_dump($this->destinationmarker);				//Debuging
		$this->process($this->startmarker, 0);

		//var_dump($this->closelist);							//Debuging
		//var_dump($this->traversedmarker);					//Debuging

		return $this->generateResult();
	}

	private function generateResult(){
		$res = new PathResult($this->traveled);
		foreach ($this->traversedmarker as $key => $value) {
			$res->Add($this->graph->getMarker($key)->getMarker()->getCoordinate());
		}
		return $res;
	}

	private function process(MarkerResult $marker, $traveled){
		if ($marker->getId() == $this->destinationmarker->getId()){
			$this->finish = true;
			$this->traveled = $traveled;
			$this->traversedmarker[$marker->getId()] = Arrays::Length($this->traversedmarker);
		}else{
			//get distance
			$distances = [];
			foreach ($marker->getMarker()->getLink() as $key => $value) {
				$this->tempmarker = $this->graph->getMarkerByLink($marker, $key);
				//var_dump($this->graph->getDistance($marker, $key));
				//var_dump($this->getDistance($this->tempmarker, $this->destinationmarker));
                               // $G = "G : " . number_format($this->graph->getDistance($marker, $key, 3) * 1000);
				//var_dump($G);
				//$H = "H : ". number_format($this->getDistance($this->tempmarker, $this->destinationmarker, 3) * 1000);
				//var_dump($H);
				$distances[$key] =  $this->graph->getDistance($marker, $key) + $this->getDistance($this->tempmarker, $this->destinationmarker);
                              // $F = "F : ". number_format($distances[$key],3)*1000;
				//var_dump($F);
			}
			//sort distance
			//var_dump($distances);
			Arrays::assocSort($distances);
			//var_dump($distances);								//Debuging
			
			$this->traversedmarker[$marker->getId()] = Arrays::Length($this->traversedmarker);
			
			foreach ($distances as $key => $value) {
				/*var_dump($value);*/
				if (Arrays::isKeyExist($this->closelist, $marker->getMarker()->getLink()[$key]->getPathId())){
					continue;
				}
				$this->closelist[$marker->getMarker()->getLink()[$key]->getPathId()] = $traveled;

				$this->tempmarker = $this->graph->getMarkerByLink($marker, $key);
				//var_dump($marker->getId());										//Debuging
				//var_dump($key);													//Debuging
				//var_dump($this->tempmarker->getId());							//Debuging
				//echo "--------------------------------------<br>";				//Debuging
				/*var_dump($marker);
				var_dump($this->tempmarker);*/
				if (Arrays::isKeyExist($this->traversedmarker, $this->tempmarker->getId())) {
					continue;
				}

				$this->process($this->tempmarker, $traveled + $this->graph->getDistance($marker, $key));
				
				if ($this->finish){
					break;
				}
			}
			if (!$this->finish)
				unset($this->traversedmarker[$marker->getId()]);
		}
	}

	private function getDistance(MarkerResult $marker1, MarkerResult $marker2){
		return Math::Haversine(
			$marker1->getMarker()->getCoordinate(),
			$marker2->getMarker()->getCoordinate()
		);
	}
}