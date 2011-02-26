package controllers;

import play.*;
import play.mvc.*;

import java.util.*;

import models.*;

import com.kjetland.ddsl.model.*;

public class Application extends Controller {
    
    public static class ServiceLocationInfo{
        public String url;
        public Double quality;
        public Date lastUpdated;
        public String ip;
    }
    
    public static class ServiceInfo{
        public String env;
        public String type;
        public String name;
        public String version;
        public List<ServiceLocationInfo> locations = new ArrayList<ServiceLocationInfo>();
    }

    public static void index() {
        
        
        List<ServiceInfo> services = new ArrayList<ServiceInfo>();
        
        for( ServiceWithLocations service :  DDSL.getClient().getAllAvailableServices() ){
            ServiceInfo si = new ServiceInfo();
            si.env = service.id().environment();
            si.type = service.id().serviceType();
            si.name = service.id().name();
            si.version = service.id().version();
            
            for( ServiceLocation location : service.locations()){
                ServiceLocationInfo sl = new ServiceLocationInfo();
                sl.url = location.url();
                sl.quality = location.quality();
                sl.lastUpdated = location.lastUpdated().toDate();
                sl.ip = location.ip();
                si.locations.add( sl);
            }
            
            services.add( si );
        }
        render(services);
    }

}