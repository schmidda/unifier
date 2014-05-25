/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package unifier;

import java.util.Arrays;
import java.util.Set;
import static unifier.HTMLIndex.website;

/**
 * Index of a list of incipits. Generate alphabetic headings for each letter.
 * @author desmond
 */
public class IncipitIndex extends HTMLIndex
{
    public void add( String incipit, String hKey )
    {
        if ( !map.containsKey(incipit) )
            map.put( escape(incipit), hKey );
    }
    char firstChar( String incipit )
    {
        char letter = 0;
        for ( int i=0;i<incipit.length();i++ )
        {
            letter = incipit.charAt(i);
            if ( Character.isLetter(letter) )
            {
                letter = Character.toUpperCase(letter);
                break;
            }
        }
        return letter;
    }
    String compose()
    {
        // go through all the incipits, creating letter headings
        char letter = 0;
        Set<String> keys = map.keySet();
        String[] array = new String[keys.size()];
        keys.toArray( array );
        Arrays.sort( array, new IncipitComparator() );
        StringBuilder sb = new StringBuilder();
        sb.append("{\n\"body\": \"");
        sb.append("<div class=\\\"listContainer\\\">\\n");
        sb.append("<ul class=\\\"expList\\\">\\n");
        for ( int i=0;i<array.length;i++ )
        {
            String incipit = array[i];
            char newLetter = firstChar( incipit );
            if ( newLetter != letter )
            {
                if ( letter != 0 )
                    sb.append("</ul>");
                letter = newLetter;
                sb.append("<li>");
                sb.append( letter );
                sb.append("</li>\\n");
                sb.append("<ul>\\n");
            }
            sb.append("<li>");
            sb.append("<a href=\\\"");
            sb.append(website);
            sb.append("/mvdsingle?DOC_ID=english/harpur/");
            sb.append( (String)map.get(incipit) );
            sb.append("\\\">");
            sb.append( incipit );
            sb.append("</a>");
            sb.append("</li>\\n");
        }
        if ( letter != 0 )
            sb.append("</ul>");
        sb.append("</ul>");
        sb.append("</div>");
        sb.append("\",\n\"format\": \"TEXT/HTML\"\n}");
        return sb.toString();
    }
}
