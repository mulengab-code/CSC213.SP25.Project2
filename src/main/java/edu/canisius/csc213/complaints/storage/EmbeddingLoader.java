package edu.canisius.csc213.complaints.storage;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.collections4.map.HashedMap;
import org.springframework.util.ResourceUtils;

import java.io.*;
import java.util.*;

public class EmbeddingLoader {

    /**
     * Loads complaint embeddings from a JSONL (newline-delimited JSON) file.
     * Each line must be a JSON object with:
     * {
     *   "complaintId": <long>,
     *   "embedding": [<double>, <double>, ...]
     * }
     *
     * @param jsonlStream InputStream to the JSONL file
     * @return A map from complaint ID to its embedding vector
     * @throws IOException if the file cannot be read or parsed
     */
    public static Map<Long, double[]> loadEmbeddings(InputStream jsonlStream) throws IOException {
        // TODO: Implement parsing of JSONL to extract complaintId and embedding

       // List<Map<String,Object>> loadedEmbeddings = new ArrayList<>();
        //File embeddingsFile = ResourceUtils.getFile("classpath:"+jsonlPath);

        Map<Long,double[]> hashEmbedding = new HashedMap<>();

        try(BufferedReader reader = new BufferedReader(new InputStreamReader(jsonlStream))){
            String line;
            int j =0;
            while ((line = reader.readLine()) != null){
                //System.out.println(line);
                Map<String, Object> embeddingMap = new ObjectMapper().readValue(line, new TypeReference<Map<String, Object>>() {});
                ///loadedEmbeddings.add(embeddingMap);
                Long cId =Long.valueOf(embeddingMap.get("id").toString()).longValue();
                List<Double> embs = (ArrayList)embeddingMap.get("embedding");

                double[] embeddingsDboule = new double[embs.size()];
                for(int i=0;i<embs.size();i++){
                    embeddingsDboule[i] = embs.get(i);
                }
                hashEmbedding.put(cId,embeddingsDboule);
                j++;
            }

            //System.out.println("embeddings found: "+loadedEmbeddings.size()+" and "+j);

        }catch (IOException e){
            e.printStackTrace();
        }
        return hashEmbedding;
    }

}
