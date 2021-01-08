    package com.iit.bo.custom.impl;

    import com.iit.bo.custom.AdminHomeBO;
    import com.iit.dao.custom.CandidateDAO;
    import com.iit.dao.custom.impl.CandidateDAOImpl;
    import com.iit.dao.custom.impl.VoterDAOImpl;
    import com.iit.db.DBConnection;
    import com.iit.model.Candidate;
    import com.iit.model.Voter;
    import javafx.collections.ObservableList;

    import java.sql.Connection;
    import java.sql.SQLException;
    import java.util.ArrayList;
    import java.util.HashMap;

    public class AdminHomeBOImpl implements AdminHomeBO {
        @Override
        public boolean checkVotingStart() throws SQLException, ClassNotFoundException {
            return new CandidateDAOImpl().getCount()>0 && new VoterDAOImpl().getCount()>0;
        }

        @Override
        public HashMap<String , HashMap<String , Integer>> getVotersStats() throws SQLException, ClassNotFoundException {

            //to store gender detail hashmaps and names of them
            HashMap<String , HashMap<String , Integer>> hashMaps = new HashMap<>();

            //to store relevnet gender's details order by their province
            HashMap<String , Integer> hashMapAll = new HashMap<>();
            HashMap<String , Integer> hashMapFemale = new HashMap<>();
            HashMap<String , Integer> hashMapMale = new HashMap<>();


            //get all of the votes in the database
            ObservableList<Voter> all = new VoterDAOImpl().getAll();

            //select one by one of the observablelist
            for (Voter voter : all) {
                if (hashMapAll.containsKey(voter.getProvince())){
                    hashMapAll.computeIfPresent(voter.getProvince(), (key, oldValue) -> oldValue +1);
                    addGendersMap(voter ,hashMapFemale , hashMapMale);
                }else {
                    hashMapAll.put(voter.getProvince() , 1);
                    addGendersMap(voter ,hashMapFemale , hashMapMale);
                }

            }

            //insert hashmaps into the hashMaps
            hashMaps.put("gender" , hashMapAll);
            hashMaps.put("female" , hashMapFemale);
            hashMaps.put("male" , hashMapMale);
    
            return hashMaps;
        }

        //use to update hashmaps of the male and female
        private void addGendersMap(Voter voter, HashMap<String, Integer> hashMapFemale, HashMap<String, Integer> hashMapMale) {
            if (voter.getGender().equals("female")){
                if (hashMapFemale.containsKey(voter.getProvince())){
                    hashMapFemale.computeIfPresent(voter.getProvince(), (key, oldValue) -> oldValue +1);
                }else {
                    hashMapFemale.put(voter.getProvince() , 1);
                }
            }else {
                if (hashMapMale.containsKey(voter.getProvince())){
                    hashMapMale.computeIfPresent(voter.getProvince(), (key, oldValue) -> oldValue +1);
                }else {
                    hashMapMale.put(voter.getProvince() , 1);
                }
            }
        }


        @Override
        public int getCandidateCount() throws SQLException, ClassNotFoundException {
            return new CandidateDAOImpl().getCount();
        }

        @Override
        public int getVoterCount() throws SQLException, ClassNotFoundException {
            return new VoterDAOImpl().getCount();
        }

        @Override
        public int[] getVoterCountByGender() throws SQLException, ClassNotFoundException {
            return new VoterDAOImpl().getCountByGender();
        }

        @Override
        public Voter getVoter(String nic) throws SQLException, ClassNotFoundException {
            return new VoterDAOImpl().search(nic);
        }

        @Override
        public boolean finalizeVote(String party, int voter_id) throws SQLException, ClassNotFoundException {

            //get the connection and avoid the auto commit
            Connection connection = DBConnection.getInstance().getConnection();
            connection.setAutoCommit(false);

            //first check the does candiate updated of not
            if (new CandidateDAOImpl().increseCandidate(party)){
                //then update the voter details of the database and set commit true after it will be done
                if (new VoterDAOImpl().setVote(voter_id)){
                    connection.setAutoCommit(true);
                    return true;
                }else {
                    connection.rollback();
                    connection.setAutoCommit(true);
                    return false;
                }
            }else {
                connection.setAutoCommit(true);
                return false;
            }
        }

        @Override
        public ObservableList<Candidate> getFinalResults() throws SQLException, ClassNotFoundException {
            return new CandidateDAOImpl().getAll();
        }
    }
