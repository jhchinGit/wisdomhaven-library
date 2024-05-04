pipeline {
    agent any
    stages {
        stage('Build') { 
            steps {
                bat 'mvn -f authenticator/pom.xml -B clean package'
                bat 'mvn -f book/pom.xml -B clean package'
                bat 'mvn -f borrower/pom.xml -B clean package'
                bat 'mvn -f borrowing/pom.xml -B clean package'
            }
        }
    }
}