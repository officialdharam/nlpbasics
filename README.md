# nlpbasics
This project is a basic demo of few of the NLP applications. 

# Getting Started
* Checkout the Code
* Import in Eclipse
* Add tomcat server in Eclipse
* Run/Debug on Server

# Using the Training Corpus
The training corpus is located at https://github.com/officialdharam/nlpbasics/tree/master/nlpdemo/WebContent/trainingdata . You can place it anywhere on your local system and edit the paths in the code as required.

# About the Spelling Correction Demo
It only corrrects non-word spelling errors based on a dictionary included in the training data. If a new word is encountered, it asks you to add it in the dictionary which is not persisted in the training file, but only in memory. If you restart the server, you will need to re add it in the dictionary. 

Alternatively you may edit the code to add the new word to the physical file using the code.

# About the Text Classification Demo
It uses Naive Bayes Algorithm with Laplace's Add one Smoothing. The code is generic enough and the training data include small corpus from the IMDB movie review.

You can alternatively write a small client for your own domain and supply training files. For a sample, check the client in.techieme.nlp.sentimentanalysis.MovieReviewClassification.java

# Disclaimer
This work is just for demo purpose and guarantees no accuracy for a commercial application. This is used to explain NLP as a concept to beginners. 

# Contribution
If you are planning to contribute to improve this project as a beginner then please contact me.

# License
Everything in this repo is free to use, distribute and edit without any permission from me. 


