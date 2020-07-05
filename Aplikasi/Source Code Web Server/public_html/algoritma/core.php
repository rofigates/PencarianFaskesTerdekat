<?php
	class Number{
		public static function getPrecisionLength($floatValue){
			if (is_numeric($floatValue)){
				if (is_float($floatValue)){
					return strlen(explode(".", strval($floatValue))[1]);
				}
				return 0;
			}
			throw new Exception("Parameter is not numeric", 1);
		}
	}

	class Arrays {
		public static function Pop(array &$array){
			if (Arrays::Length($array) > 0){
				return array_pop($array);
			}
			throw new Exception("Parameter \$array is empty array", 1);
		}

		public static function Push(array &$array, $value){
			return array_push($array, $value);
		}

		public static function Length(array $array){
				return count($array);
		}

		public static function isKeyExist(array $array, $key){
			return array_key_exists($key, $array);
		}

		public static function getKeyIndex(array $array, $value){
			return array_search($value, $array);
		}

		public static function assocSort(array &$array){
			asort($array);
		}

		public static function Sort(array &$array){
			sort($array);
		}
	}

	class Validation{
		private static $errorMessage;
		
		public static function Check($param){
			if (isset($param)){
				if (!empty($param)){
					return true;
				}
				$errorMessage = "Parameter is empty";
				return fasle;
			}
			$errorMessage = "Parameter is not set";
			return false;
		}

		public static function getMessage(){
			return self::$errorMessage;
		}

		public static function keyExists($key, $array){
			if (is_array($array)){
				return array_key_exists($key, $array);
			}
			throw new Exception("Parameter \$array is not an array", 1);
		}

		public static function getKey($value, $array){
			if (is_array($array)){
				return array_search($value, $array);
			}
			throw new Exception("Parameter \$array is not an array", 1);
		}
	}

	class Math{
	public static function Haversine(LatLng $point1, LatLng $point2, $sphereRadius = 6371){
		return 2 * $sphereRadius * asin(
			sqrt(
				pow(sin((deg2rad($point1->getLat()) - deg2rad($point2->getLat())) / 2), 2) +
				cos(deg2rad($point1->getLat())) *
				cos(deg2rad($point2->getLat())) *
				pow(sin((deg2rad($point1->getLng()) - deg2rad($point2->getLng())) / 2), 2)
			)
		);
	}

	// public static function Euclid(LatLng $point1, LatLng $point2, $sphereRadius = 6371){
	// 	return sqrt(pow($point1->getLat() - $point2->getLat(), 2) + pow($point1->getLng() - $point2->getLng(), 2)) * deg2rad($sphereRadius);
	// }
}

class File{
	public static function Readfile($path){
		$handle = fopen($path, 'r') or die('Cannot open file:  '.$path);
		$data = fread($handle,filesize($path));
		fclose($handle);
		return $data;
	}

	private static function myEncode($json){
        return "\"".str_replace("\"", "\\\"", rtrim(ltrim($json, "\""), "\""))."\"";
	}
}