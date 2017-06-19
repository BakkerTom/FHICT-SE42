/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package auction.web;

import javax.xml.ws.Endpoint;

/**
 *
 * @author Stijn
 */
public class PublishAuctionService {
    private static final String urlAuction = "http://localhost:8080/Auction";
    private static final String urlRegistration = "http://localhost:8080/Registration";

    public static void main(String[] args) {
        Endpoint.publish(urlAuction, new Auction());
        Endpoint.publish(urlRegistration, new Registration());
    }
}
