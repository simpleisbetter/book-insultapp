package org.openshift;

import javax.faces.bean.RequestScoped;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import java.util.HashMap;

@RequestScoped
@Path("/insult")
public class InsultResource {
    @GET()
    @Produces("application/json")
    public HashMap<String, String> getInsult() {
        HashMap<String, String> theInsult = new HashMap<>();
        theInsult.put("insult", new InsultGenerator().generateInsult());
        return theInsult;
    }
}
