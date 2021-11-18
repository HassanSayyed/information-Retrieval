# Overview:
 ###### Phase 1 StopList
 1. Read data from the files, supported files :
   - Text Files
   - PDF files (PDF Reader Library)
   - Images ( All types of Images suppored by Tesseract-**OCR** -> **O**ptical **C**harachter **R**ecognition engine)
 2. Removing stopwords and handling two symboles '@' and '.'
 3. Write data files results in STP directory as Text files
 
 ###### Phase 2 Stemming
 1. Read data from STP directory
 2. Removal of suffixes using Porter Stemmer Algorithm
 3. Write the results in SFX directory as Text files
 
 # System Requirements:
 1. Java 13, you can download it from https://www.oracle.com/java/technologies/javase/jdk13-archive-downloads.html
 2. PDF Reader Library, you can download it from https://downloads.apache.org/pdfbox/2.0.24/
 3. Tesseract-OCR, you can download it from https://tesseract-ocr.github.io/tessdoc/Downloads.html
  
