
package za.co.mie.controller;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface ProcessRequest {
    void processTheRequest(HttpServletRequest request, HttpServletResponse response);
    void processTheResponse(HttpServletRequest request, HttpServletResponse response);
}

