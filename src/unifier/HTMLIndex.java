/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package unifier;
import java.util.HashMap;

/**
 * Abstract class for various index pages
 * @author desmond
 */
public abstract class HTMLIndex 
{
    protected static String website = "http://dev.austese.net/harpur";
    protected HashMap<String,Object> map;
    protected HTMLIndex()
    {
        map = new HashMap<String,Object>();
    }
    abstract String compose();
    String escape( String raw )
    {
        return raw.replace("\"", "\\\"");
    }
}
