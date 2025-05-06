package edu.canisius.csc213.complaints.service;

import edu.canisius.csc213.complaints.model.Complaint;

import java.util.*;

public class ComplaintSimilarityService {

    private final List<Complaint> complaints;

    public ComplaintSimilarityService(List<Complaint> complaints) {
        this.complaints = complaints;
    }

    public List<Complaint> findTop3Similar(Complaint target) {
        // TODO: Return top 3 most similar complaints (excluding itself)
        List<Complaint> complaintList = new ArrayList<>();
        Map<Double,Complaint> similarities = new HashMap<>();

        for(int i=0;i<this.complaints.size();i++){
            Complaint complaint = this.complaints.get(i);
            double similarity = this.cosineSimilarity(target.getEmbedding(),complaint.getEmbedding());
            similarities.put(similarity,complaint);
        }
        List<Double> sortedKeys = new ArrayList(similarities.keySet());
        Collections.sort(sortedKeys);
        for(int i=0;i<sortedKeys.size();i++){
            double key = sortedKeys.get(i);
            complaintList.add(similarities.get(key));
            if(complaintList.size() > 2) break;
        }

        return complaintList;
    }

    private double cosineSimilarity(double[] a, double[] b) {
        // TODO: Implement cosine similarity
        double dotProduct = 0.0;
        double normA = 0.0;
        double normB = 0.0;
        for (int i = 0; i < a.length; i++) {

            dotProduct += a[i] * b[i];
            normA += Math.pow(a[i], 2);
            normB += Math.pow(b[i], 2);
        }
        return dotProduct / (Math.sqrt(normA) * Math.sqrt(normB));

    }

    private static class ComplaintWithScore {
        Complaint complaint;
        double score;

        ComplaintWithScore(Complaint c, double s) {
            this.complaint = c;
            this.score = s;
        }
    }
}
