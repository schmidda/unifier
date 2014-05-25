/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package unifier;
import java.util.ArrayList;

/**
 * Represent an entry in the ALTable
 * @author desmond
 */
public class ALEntry 
{
    String alURL;
    String incipit;
    /** end position after read*/  
    int pos;
    ArrayList<String> subjects;
    public void addSubject( String subject )
    {
        if ( subjects == null )
            subjects = new ArrayList<String>();
        subjects.add( subject );
    }
    public String getSubjects()
    {
        StringBuilder sb = new StringBuilder();
        if ( subjects != null )
        {
            for ( int i=0;i<subjects.size();i++ )
            {
                sb.append("\"");
                sb.append( subjects.get(i) );
                sb.append("\"");
                if ( i < subjects.size()-1 )
                    sb.append(",");
            }
        }
        return sb.toString();
    }
    static String readLine( String contents, int start )
    {
        int i = start;
        StringBuilder sb = new StringBuilder();
        while ( i < contents.length() )
        {
            char token = contents.charAt(i++);
            if ( token=='\n' )
                break;
            else
                sb.append( token );
        }
        return sb.toString();
    }
    static ALEntry read( String contents, int start )
    {
        int i = start;
        ALEntry ale = new ALEntry();
        int nEmpties = 0;
        while ( nEmpties < 2 && i < contents.length() )
        {
            String line = readLine( contents, i );
            i += line.length() + 1;
            if ( line.length()==0 )
                nEmpties++;
            else if ( line.contains(":") )
            {
                nEmpties = 0;
                String[] parts = line.split(":");
                if ( parts.length>=2 )
                {
                    if ( parts[0].equals("Keyword") )
                        ale.addSubject( parts[1].trim() );
                    else if ( parts[0].equals("AustLit Ref")
                            && parts[1].trim().equals("http")
                            && parts.length>2 )
                        ale.alURL = parts[2].substring(20);
                    else if ( parts[0].equals("First Line") )
                        ale.incipit = parts[1];
                }
            }
        }
        ale.pos = i;
        return ale;
    }
    int getPos()
    {
        return pos;
    }
    boolean isComplete()
    {
        return alURL != null && alURL.length() > 0;
    }
}
