/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package unifier;
import java.util.Comparator;
/**
 *
 * @author desmond
 */
public class IncipitComparator implements Comparator<String>
{
    public int compare(String o1, String o2)
    {
        while ( o1.length() > 0 && !Character.isLetter(o1.charAt(0)) )
            o1 = o1.substring(1);
        while ( o2.length() > 0 && !Character.isLetter(o2.charAt(0)) )
            o2 = o2.substring(1);
        return o1.compareToIgnoreCase(o2);
    }
    public boolean equals(Object obj)
    {
        return this.equals(obj);
    }
}
