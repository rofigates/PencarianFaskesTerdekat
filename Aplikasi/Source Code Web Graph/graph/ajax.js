function loadDoc(asd) {
  var xhttp;
  var data = {
    name: "dika",
    age: "22",
    address: "malang"};

  if (window.XMLHttpRequest) {
    // code for modern browsers
    xhttp = new XMLHttpRequest();
    } else {
    // code for IE6, IE5
    xhttp = new ActiveXObject("Microsoft.XMLHTTP");
  }
  
  //event
  xhttp.onreadystatechange = function() {
    if (xhttp.readyState == 4 && xhttp.status == 200) {
      document.getElementById("demo").innerHTML = xhttp.responseText;
      asd(xhttp.responseText);
    }else{
      console.log
    }
  };
  
  xhttp.open("POST", "services.php", true);
  console.log(JSON.stringify(data));
  xhttp.send(JSON.stringify(data));
}

function dakocan(req){
  console.log(JSON.stringify(req));
}

class kambingjantan{

  constructor(){
    this.a = 1;
    this.b = 2;
    this.c = 3;
    this.d = {};
    this.d["satu"] = new kambingbetina("satu a", "satu b", "satu c");
    this.d["dua"] = new kambingbetina("dua a", "dua b", "dua c");
    this.d["tiga"] = new kambingbetina("tiga a", "tiga b", "tiga c");
    
    this.d[0] = new kambingbetina("satu a", "satu b", "satu c");
    this.d[1] = new kambingbetina("dua a", "dua b", "dua c");
    this.d[2] = new kambingbetina("tiga a", "tiga b", "tiga c");

    this.e = [];
    this.e.push(new kambingbetina("array satu a", "array satu b", "array satu c"));
    this.e.push(new kambingbetina("array dua a", "array dua b", "array dua c"));
    this.e.push(new kambingbetina("array tiga a", "array tiga b", "array tiga c"));
    this.e["eek"] = new kambingbetina("array tiga a", "array tiga b", "array tiga c");
  }
}

class kambingbetina{
  constructor(a,b,c){
    this.a1 = a;
    this.a2 = b;
    this.a3 = c;
  }
}

var aaa = new kambingjantan();