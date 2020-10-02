package com.example.liverpooldemo.WebService;

/**
 * Esta pinchi interface la implementan las clases que quieran
 * ser participes a ser contestadas por el backEnd
 */
public interface WebResponseDelegate {
    /**
     * Response en case de exito
     * @param response  el objeto respuesta
     * @param code el codigo en case de necesitarlo
     */
    void didFInish(Object response, Object code);

    /**
     * El pinchi error
     * @param response
     * @param code
     */
    void didFinishWithError(Object endpoint, Object response, int code);
}
