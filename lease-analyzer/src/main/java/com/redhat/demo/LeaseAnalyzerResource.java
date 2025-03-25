package com.redhat.demo;

import java.io.IOException;
import java.nio.file.Files;
import java.util.Base64;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.jboss.logging.Logger;
import org.jboss.resteasy.reactive.RestForm;
import org.jboss.resteasy.reactive.multipart.FileUpload;

import dev.langchain4j.data.pdf.PdfFile;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/pdf")
public class LeaseAnalyzerResource {

    @Inject
    LeaseAnalyzer analyzer;

    private static final Logger LOG = Logger.getLogger(LeaseAnalyzerResource.class);

    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/upload")
    @Operation(summary = "Upload and analyze a lease document", description = "Uploads a PDF lease document and analyzes it using Google Gemini AI to extract key information")
    @RequestBody(content = @Content(mediaType = MediaType.MULTIPART_FORM_DATA, schema = @Schema(implementation = FileUpload.class)))
    @APIResponse(responseCode = "200", description = "Successfully analyzed lease document", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = LeaseReport.class)))
    public LeaseReport upload(@RestForm("file") FileUpload fileUploadRequest) {
        
        final String fileName = fileUploadRequest.fileName();
        LOG.infof("Uploading file: %s", fileName);

        try {
            // Convert input stream to byte array for processing
            byte[] fileBytes = Files.readAllBytes(fileUploadRequest.filePath());

            // Encode PDF content to base64 for transmission
            String documentEncoded = Base64.getEncoder().encodeToString(fileBytes);

            LOG.info("Google Gemini analyzing....");
            long startTime = System.nanoTime();

            LeaseReport result = analyzer.analyze(PdfFile.builder().base64Data(documentEncoded).build());

            long endTime = System.nanoTime();
            LOG.infof("Google Gemini analyzed in %.2f seconds: %s", (endTime - startTime) / 1_000_000_000.0, result);

            return result;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
