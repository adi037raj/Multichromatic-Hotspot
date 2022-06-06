package multichromatic;

import java.util.Comparator;

class PatternComperator implements Comparator<Patterns> {

    // Used for sorting in ascending order of
  
  synchronized  public int compare(Patterns a, Patterns b) {
        // System.out.println("pattern compator llr is a and b "+a.llr+" "+ b.llr);
         if(a.llr <b.llr)
        return 1;
        else if(a.llr>b.llr)
        return -1;


        return 0;
    }
}
class PatternComperator2 implements Comparator<ChangeDetection>{

    @Override
    public int compare(ChangeDetection a, ChangeDetection b) {
        
        if(a.llr <b.llr)
        return 1;
        else if(a.llr>b.llr)
        return -1;


        return 0;
    }
    
}