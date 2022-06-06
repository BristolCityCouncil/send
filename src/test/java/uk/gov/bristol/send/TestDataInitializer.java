package uk.gov.bristol.send;
import java.util.ArrayList;
import java.util.List;
import uk.gov.bristol.send.model.Assessment;
import uk.gov.bristol.send.model.NeedStatement;
import uk.gov.bristol.send.model.Provision;
import uk.gov.bristol.send.model.ProvisionLookUp;
import uk.gov.bristol.send.model.SelectedProvision;
import uk.gov.bristol.send.model.Need;


public class TestDataInitializer {
    
    public ArrayList<Assessment> initAssessments(){
        ArrayList<Assessment> assessments= new ArrayList<Assessment>();
        List<NeedStatement> selectedNeedStatements = List.of();
        List<SelectedProvision> selectedProvisions = List.of();   
        Assessment assess1 = new Assessment(1, "A123456789012B", "tinatestington@bristol.gov.uk", "new", "James Woods High", selectedNeedStatements, selectedProvisions); 
        Assessment assess2 = new Assessment(1, "Z123456788888B", "tinatestington@bristol.gov.uk", "new", "Springfield Elementary", selectedNeedStatements, selectedProvisions); 
        Assessment assess3 = new Assessment(1, "A123456789222", "tommytestington@bristol.gov.uk", "new", "Bash Street", selectedNeedStatements, selectedProvisions);         
        assessments.add(assess1);
        assessments.add(assess2);
        assessments.add(assess3);
        return assessments;
    }


    public ArrayList<Need> initNeeds(){
        ArrayList<Need> needs = new ArrayList<Need>();    
        Need need1 = new Need(1, 1, "firstAreaId", "first area label", 1, "firstSubAreaId", "first sub area label");
        Need need2 = new Need(1, 1, "firstAreaId", "first area label", 1, "secondSubAreaId", "second sub area label");
        Need need3 = new Need(1, 1, "secondAreaId", "second area label", 1, "thirdSubAreaId", "third sub area label");
        needs.add(need1);
        needs.add(need2);
        needs.add(need3);

        return needs;
    }


    public ArrayList<NeedStatement> initNeedStatements(){
        ArrayList<NeedStatement> needStatements = new ArrayList<NeedStatement>();
        //value of areaId doesn't really matter here because NeedStatements are found by subArea not area.
        NeedStatement ns1 = new NeedStatement("areaId", "areaLabel", "firstSubAreaId", "first sub area label", "firstCategoryId", "first category label","A1SA1SG1", "A1SA1SG1STA1", "first statement label", "a"); 
        NeedStatement ns2 = new NeedStatement("areaId", "areaLabel", "secondSubAreaId", "second sub area label", "fourthCategoryId", "fourth category label","A1SA2SG1", "A1SA2SG1STB3", "second statement label", "a"); 
        NeedStatement ns3 = new NeedStatement("areaId", "areaLabel", "firstSubAreaId", "first sub area label", "firstCategoryId", "first category label","A1SA1SG1", "A1SA1SG1STA2", "third statement label", "b"); 
        NeedStatement ns4 = new NeedStatement("areaId", "areaLabel", "secondSubAreaId", "second sub area label", "fourthCategoryId", "fourth category label","A1SA2SG1", "A1SA2SG1STB4", "fourth statement label", "b"); 
        NeedStatement ns5 = new NeedStatement("areaId", "areaLabel", "firstSubAreaId", "first sub area label", "fifthCategoryId", "fifth category label","A1SA1SG1", "A1SA1SG1STC1", "fifth statement label", "c"); 
        needStatements.add(ns1);
        needStatements.add(ns2);
        needStatements.add(ns3);
        needStatements.add(ns4);
        needStatements.add(ns5);

        return needStatements;
    }


    public ArrayList<NeedStatement> initNeedStatementsForGroup(String groupId){
        ArrayList<NeedStatement> needStatements = new ArrayList<NeedStatement>();
        //value of areaId doens't really matter here because NeedStatements are found by subArea not area.
        NeedStatement ns1 = new NeedStatement("areaId", "areaLabel", "firstSubAreaId", "first sub area label", "firstCategoryId", "first category label", groupId,  "A1SA1SG1STA1", "first statement label", "a"); 
        NeedStatement ns3 = new NeedStatement("areaId", "areaLabel", "firstSubAreaId", "first sub area label", "firstCategoryId", "first category label", groupId, "A1SA1SG1STC2", "third statement label", "c"); 
        needStatements.add(ns1);        
        needStatements.add(ns3);

        return needStatements;
    }


    public ArrayList<Provision> initProvisions(){
        ArrayList<Provision> provisions = new ArrayList<Provision>();
        Provision p1 = new Provision(1, "A", "PGID1", "Specialist Professional Provision", "1", "PTID1", "Specialist professional advice and support 1", "PGID1_PTID1_PS1", "Supervision or coaching of staff by professionals.");
        Provision p2 = new Provision(1, "B", "PGID1", "Specialist Professional Provision", "2", "PTID2", "Specialist professional supervision/overseeing of intervention", "PGID1_PTID2_PS3", "Supervision by a specialist professional with contact for 2 hours.");
        Provision p3 = new Provision(1, "C", "PGID1", "Specialist Professional direct support", "3", "PTID3", "Specialist professional direct support", "PGID1_PTID3_PS5", "Direct therapy/intervention e.g. music, drama, art, play therapy, speech and language therapy, occupational therapy, physiotherapy on a regular basis");
        Provision p4 = new Provision(1, "C", "PGID2", "Group & Individual Intervention", "5", "PTID9", "Post-16 tutoring", "Tutoring for over 16s", "PGID2_PTID9_PS1");        
        provisions.add(p1);        
        provisions.add(p2);        
        provisions.add(p3);
        provisions.add(p4);
        
        return provisions;
    }

    public ArrayList<ProvisionLookUp> initProvisionsLookUp() {
        ArrayList<ProvisionLookUp> provisionsLookUp = new ArrayList<ProvisionLookUp>();
        ProvisionLookUp pl1 = new ProvisionLookUp(1, "expressiveCommunication", "PTID1,PTID2,");
        ProvisionLookUp pl2 = new ProvisionLookUp(1, "receptiveCommunication", "PTID11,PTID12,PTID13,");
        ProvisionLookUp pl3 = new ProvisionLookUp(1, "hearing", "PTID31,PTID32,PTID33,PTID34,PTID41,");
        provisionsLookUp.add(pl1);
        provisionsLookUp.add(pl2);
        provisionsLookUp.add(pl3);

        return provisionsLookUp;
    }

}
