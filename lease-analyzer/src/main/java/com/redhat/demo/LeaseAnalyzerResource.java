package com.redhat.demo;

import java.io.IOException;
import java.nio.file.Files;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.jboss.logging.Logger;
import org.jboss.resteasy.reactive.RestForm;
import org.jboss.resteasy.reactive.multipart.FileUpload;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

/**
 * REST resource for analyzing lease documents using Google Gemini AI.
 * Provides endpoints for uploading and analyzing PDF lease agreements.
 */
@Path("/analyze-lease")
public class LeaseAnalyzerResource {

    private static final Logger LOG = Logger.getLogger(LeaseAnalyzerResource.class);

    @Inject
    LeaseAnalyzer analyzer;

    /**
     * Uploads and analyzes a PDF lease document.
     * 
     * @param fileUploadRequest The multipart form request containing the PDF file
     * @return JSON string containing the analyzed lease information
     */
    @PUT
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces({ MediaType.APPLICATION_JSON })
    @Path("/upload")
    @Operation(summary = "Upload and analyze a lease document", description = "Uploads a PDF lease document and analyzes it using Gemma AI to extract key information")
    @RequestBody(content = @Content(mediaType = MediaType.MULTIPART_FORM_DATA, schema = @Schema(implementation = FileUpload.class)))
    @APIResponse(responseCode = "200", description = "Successfully analyzed lease document", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = LeaseReport.class)))
    public LeaseReport upload(@RestForm("file") FileUpload fileUploadRequest) {
        final String fileName = fileUploadRequest.fileName();
        LOG.infof("Uploading file: %s", fileName);

        try {
            byte[] fileBytes = Files.readAllBytes(fileUploadRequest.filePath());

            // Leer PDF y extraer texto
            try (PDDocument document = PDDocument.load(fileBytes)) {
                String leaseText = new PDFTextStripper().getText(document);
                LOG.info("Extracted text: " + leaseText);

                LOG.info("Gemma analyzing....");
                long startTime = System.nanoTime();

                LeaseReport result = analyzer.analyze(leaseText);

                long endTime = System.nanoTime();
                LOG.infof("Gemma analyzed in %.2f seconds: %s", (endTime - startTime) / 1_000_000_000.0, result);

                return result;
            }
        } catch (IOException e) {
            throw new RuntimeException("Error processing PDF", e);
        }
    }
}
