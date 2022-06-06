package uk.gov.bristol.send;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import org.springframework.stereotype.Component;

import uk.gov.bristol.send.model.Assessment;
import uk.gov.bristol.send.model.Need;
import uk.gov.bristol.send.model.NeedStatement;


@Component
public class SendUtilities {

    public List<Need> iterableNeedsToList(Iterable<Need> iterable)  {
         List<Need> list = new ArrayList<>();
         Iterator<Need> iterator = iterable.iterator();
         iterator.forEachRemaining(list::add);
         list.sort(Comparator.comparing(a -> Integer.valueOf(a.getId())));
         return list;     
    }

    public List<NeedStatement> iterableStatementsToList(Iterable<NeedStatement> iterable)  {
         List<NeedStatement> list = new ArrayList<>();
         Iterator<NeedStatement> iterator = iterable.iterator();
         iterator.forEachRemaining(list::add);
         return list;     
    }

    public List<Assessment> iterableAssessmentsToList(Iterable<Assessment> iterable)  {
         List<Assessment> list = new ArrayList<>();
         Iterator<Assessment> iterator = iterable.iterator();
         iterator.forEachRemaining(list::add);
         return list;     
    }

}
