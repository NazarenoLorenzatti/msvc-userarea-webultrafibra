package ar.com.ultrafibra.webultrafibra.service;

import ar.com.ultrafibra.webultrafibra.response.ReferenceTableResponseRest;
import org.springframework.http.ResponseEntity;

public interface iTableReferenceService {
  
    public ResponseEntity<ReferenceTableResponseRest> getTable(String table);
}
