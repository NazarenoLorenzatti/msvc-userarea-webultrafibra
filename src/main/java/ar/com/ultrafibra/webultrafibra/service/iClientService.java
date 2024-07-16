package ar.com.ultrafibra.webultrafibra.service;

import ar.com.ultrafibra.webultrafibra.models.Client;

public interface iClientService {
    
    public Client findByDocumentNumber(String number);
}
