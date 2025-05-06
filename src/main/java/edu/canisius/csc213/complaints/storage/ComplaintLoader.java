package edu.canisius.csc213.complaints.storage;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.opencsv.CSVReader;
import com.opencsv.bean.CsvToBeanBuilder;
import edu.canisius.csc213.complaints.model.Complaint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ResourceUtils;

import java.awt.*;
import java.io.*;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Handles loading of complaints and embedding data,
 * and returns a fully hydrated list of Complaint objects.
 */
public class ComplaintLoader {



    /**
     * Loads complaints from a CSV file and merges with embedding vectors from a JSONL file.
     *
     * @param csvPath    Resource path to the CSV file
     * @param jsonlPath  Resource path to the JSONL embedding file
     * @return A list of Complaint objects with attached embedding vectors
     * @throws Exception if file reading or parsing fails
     */

    public static List<Complaint> loadComplaintsWithEmbeddings(String csvPath, String jsonlPath) throws Exception {
        // TODO: Load CSV and JSONL resources, parse, and return hydrated Complaint list
        List<Complaint> loadedComplaints = new ArrayList<Complaint>();
        File csvFile = ResourceUtils.getFile("classpath:"+csvPath);

        try (CSVReader beeReader = new CSVReader(new FileReader(csvFile))){
            String[] line;
            int i = 0;
            while ((line = beeReader.readNext()) != null){
                if(i>0) {
                    Complaint complaint = new Complaint();
                    complaint.setDateReceivedStr(line[0]);
                    complaint.setProduct(line[1]);
                    complaint.setSubProduct(line[2]);
                    complaint.setIssue(line[3]);
                    complaint.setSubIssue(line[4]);
                    complaint.setNarrative(line[5]);
                    complaint.setPublicResponse(line[6]);
                    complaint.setCompany(line[7]);
                    complaint.setState(line[8]);
                    complaint.setZipCode(line[9]);
                    complaint.setTags(line[10]);
                    complaint.setConsentProvided(line[11]);
                    complaint.setSubmittedVia(line[12]);
                    complaint.setDateSentStr(line[13]);
                    complaint.setCompanyResponse(line[14]);
                    complaint.setTimelyResponse(line[15]);
                    complaint.setDisputed(line[16]);
                    complaint.setComplaintId(Long.valueOf(line[17]).longValue());

                    loadedComplaints.add(complaint);
                }
                i++;
            }
            InputStream jsonlStream = new FileInputStream(ResourceUtils.getFile("classpath:"+jsonlPath));
            ComplaintMerger.mergeEmbeddings(loadedComplaints,EmbeddingLoader.loadEmbeddings(jsonlStream));
        }catch (IOException ex){
            ex.printStackTrace();
        }

        return loadedComplaints; // placeholder
    }
}
