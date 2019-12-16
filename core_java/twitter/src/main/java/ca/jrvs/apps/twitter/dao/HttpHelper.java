package ca.jrvs.apps.twitter.dao;

import org.apache.http.HttpResponse;

import java.net.URI;

public interface HttpHelper {

    public HttpResponse httpPost(URI uri);
    public HttpResponse httpGet(URI uri);

}
