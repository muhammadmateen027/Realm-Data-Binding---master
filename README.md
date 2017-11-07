# Realm-Data-Binding---master
This repo is totally based on Realm, Databinding and Volley. 
If you want to learn how to use Realm locally with in your project. 

# Example shown to read data from Realm: 
public List<Contact> get(View view){
        RealmResults<Contact> results = realm.where(Contact.class).findAll();
        Log.d(TAG, String.valueOf(results));

        List<Contact> retVal = new ArrayList<Contact>();
        for (int i=0; i<results.size(); i++) {
            Contact realmObj = results.get(i);
            retVal.add(realmObj);
        }
        return retVal;
    }
