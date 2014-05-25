/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package unifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;
/**
 * Create a subject index
 * @author desmond
 */
public class SubjectIndex extends HTMLIndex 
{
    public void add( String subject, String title, String hKey )
    {
        ArrayList<Subject> list;
        if ( map.containsKey(subject) )
        {
            list = (ArrayList)map.get( subject );
            list.add( new Subject(escape(title),hKey) );
        }
        else
        {
            list = new ArrayList<Subject>();
            list.add( new Subject(escape(title),hKey) );
            map.put( subject, list );
        }
    }
    String compose()
    {
        // go through all the subjects, composing a list
        Set<String> keys = map.keySet();
        String[] array = new String[keys.size()];
        keys.toArray( array );
        Arrays.sort( array );
        StringBuilder sb = new StringBuilder();
        sb.append("{\n\"body\": \"");
        sb.append("<div class=\\\"listContainer\\\">\\n");
        sb.append("<ul class=\\\"expList\\\">\\n");
        for ( int i=0;i<array.length;i++ )
        {
            String subject = array[i];
            ArrayList list = (ArrayList)map.get( subject );
            if ( list != null && list.size()>0 )
            {
                sb.append("<li>");
                sb.append( subject );
                sb.append("</li>");
                sb.append("<ul>\\n");
                for ( int j=0;j<list.size();j++ )
                {
                    Subject s = (Subject)list.get( j );
                    sb.append("<li>");
                    sb.append("<a href=\\\"");
                    sb.append(website);
                    sb.append("/mvdsingle?DOC_ID=english/harpur/");
                    sb.append(s.hKey);
                    sb.append("\\\">");
                    sb.append( s.title );
                    sb.append("</a>");
                    sb.append("</li>\\n");
                }
                sb.append("</ul>");
            }
        }
        sb.append("</ul>");
        sb.append("</div>");
        sb.append("\",\n\"format\": \"TEXT/HTML\"\n}");
        return sb.toString();
    }
}
