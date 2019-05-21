package model;

public class userdata {
String ip,name,Path;
 public   String getPath() {
	return Path;
}

public void setPath(String Path) {
	this.Path = Path;
}

  
          public String getName() {
	return name;
}

public void setName(String name) {
	this.name = name;
}

 
 
		public String getIp() {
	return ip;
}

public void setIp(String ip) {
	this.ip = ip;
}

 
		public userdata( String ip,String name,String Path){
        	 
        	  this.ip=ip;
        	  this.Path=Path;
        	  this.name=name;
          }



}
