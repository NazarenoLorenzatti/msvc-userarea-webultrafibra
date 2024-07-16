package ar.com.ultrafibra.webultrafibra.response;

import java.util.ArrayList;
import java.util.HashMap;

public class ResponseRest {

    private ArrayList<HashMap<String, String>> metadata = new ArrayList<>();

    public ArrayList<HashMap<String, String>> getMetadata() {
        return metadata;
    }
    
    public void setMetadata(String respuesta, String codigo, String info) {
		
		HashMap<String, String> mapeo = new HashMap<String, String>();
		
		mapeo.put("respuesta", respuesta);
		mapeo.put("codigo", codigo);
		mapeo.put("informacion", info);
		
		metadata.add(mapeo);
	}
}
