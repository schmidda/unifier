/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package unifier;
import java.io.File;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Set;
import java.util.Iterator;
import java.util.ArrayList;
/**
 * Read two Harpur tables and merge them where you can
 * @author desmond
 */
public class Unifier 
{
    String hTable;  // meredith's table of h-numbers to Harpur
    String alTable; // austlit table of works and urls
    HashMap<String,HEntry> hMap;
    HashMap<String,ALEntry> alMap;
    StringBuilder output;
    String readFile( String src ) throws Exception
    {
        File f = new File( src );
        if ( f.exists() )
        {
            FileReader fr = new FileReader( f );
            char[] data = new char[(int)f.length()];
            fr.read( data );
            return new String( data );
        }
        else
            throw new FileNotFoundException("couldn't find "+src );
    }
    HashMap<String,ALEntry> readAlTable( String alTable ) throws Exception
    {
        HashMap<String,ALEntry> map = new HashMap<String,ALEntry>();
        String contents = readFile( alTable );
        int pos = 0;
        while ( pos < contents.length() )
        {
            ALEntry ale = ALEntry.read( contents, pos );
            pos = ale.getPos();
            if ( ale.isComplete() )
                map.put( ale.alURL, ale );
        }
        return map;
    }
    void merge( ) throws Exception
    {
        output = new StringBuilder();
        hMap = new HashMap<String,HEntry>();
        String hStr = readFile( hTable );
        String[] hLines = hStr.split("\n");
        IncipitIndex ii = new IncipitIndex();
        SubjectIndex si = new SubjectIndex();
        IncipitIndex ti = new IncipitIndex();
        for ( int i=0;i<hLines.length;i++ )
        {
            String[] parts = hLines[i].split("\t");
            if ( parts.length==3 )
            {
                hMap.put( parts[0], new HEntry(parts[1],parts[2]) );
            }
        }
        String alStr = readFile( alTable );
        alMap = readAlTable( alTable );
        Set<String> hKeys = hMap.keySet();
        Iterator<String> iter = hKeys.iterator();
        while ( iter.hasNext() )
        {
            String hKey = iter.next();
            HEntry hEntry = hMap.get(hKey);
            if ( hEntry.austLitURL != null )
            {
                ALEntry alEntry = alMap.get(hEntry.austLitURL);
                if ( alEntry != null )
                {
                    ArrayList list = alEntry.subjects;
                    if ( list != null )
                    {
                        for ( int i=0;i<list.size();i++ )
                        {
                            si.add( (String)list.get(i), hEntry.getTitle(), hKey );
                        }
                    }
                    if ( alEntry.incipit != null )
                        ii.add( alEntry.incipit.trim(), hKey );
                }
                if ( hEntry.title != null )
                    ti.add( hEntry.getTitle(), hKey);
            }
        }
        FileOutputStream subjects = new FileOutputStream("subjects");
        FileOutputStream incipits = new FileOutputStream("incipits");
        FileOutputStream titles = new FileOutputStream("titles");
        subjects.write( si.compose().getBytes() );
        titles.write( ti.compose().getBytes() );
        incipits.write( ii.compose().getBytes() );
        subjects.close();
        titles.close();
        incipits.close();
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) 
    {
        if ( args.length == 2 )
        {
            Unifier u = new Unifier();
            u.hTable = args[0];
            u.alTable = args[1];
            try
            {
                u.merge();
            }
            catch ( Exception e )
            {
                e.printStackTrace(System.out);
            }
        }
        else
            System.out.println("usage: java Unifier <hTable> <alTable>");
    }
    
}
