package edu.canisius.csc213.complaints.storage;

import edu.canisius.csc213.complaints.model.Complaint;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ComplaintMerger {

    /**
     * Matches complaints to their corresponding embedding vectors by complaint ID.
     *
     * @param complaints List of complaints (from CSV)
     * @param embeddings Map from complaintId to embedding vector (from JSONL)
     */
    public static void mergeEmbeddings(List<Complaint> complaints, Map<Long, double[]> embeddings) {
        // TODO: For each complaint, match the ID to an embedding and set it
        for(int i=0; i< complaints.size();i++){
            Complaint complaint = complaints.get(i);
            var complainEmbedding = embeddings.entrySet()
                    .stream().filter((e)-> e.getKey() == complaint.getComplaintId()).findFirst();

            complaint.setEmbedding(complainEmbedding.get().getValue());
        }
    }

}
