pipeline {
    agent any
    stages {
        stage('Build') { 
            steps {
                sh '''
		    echo "Build authenticator"
		    mvn -f authenticator/pom.xml -B -DskipTests clean package
		'''
                sh '''
		    echo "Build book"
		    mvn -f book/pom.xml -B -DskipTests clean package
		'''
                sh '''
		    echo "Build borrower"
		    mvn -f borrower/pom.xml -B -DskipTests clean package
		'''
                sh '''
		    echo "Build borrowing"
		    mvn -f borrowing/pom.xml -B -DskipTests clean package
		'''
            }
        }
    }
}