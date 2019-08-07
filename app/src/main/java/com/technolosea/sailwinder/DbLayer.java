package com.technolosea.sailwinder;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class DbLayer {
    public DbLayer(){
        FirebaseFirestore db = FirebaseFirestore.getInstance();

    }

    public List getMarks(){
        List ret = new ArrayList();


        return ret;
    }

}
