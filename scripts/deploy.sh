echo "> 프로젝트 Build 시작"

./gradlew build

echo "> v1 디렉토리로 이동"

cd $REPOSITORY

echo "> Build 파일 복사"

cp $REPOSITORY/$PROJECT_NAME/build/libs/*.jar $REPOSITORY/

echo "> 현재 구동중인 애플리케이션 pid 확인"

CURRENT_PID=$(pgrep -fl springboot-study-project-qrna | grep jar | awk '{print $1}')

echo "현재 구동중인 애플리케이션 pid: $CURRENT_PID"

if [ -z "$CURRENT_PID" ]; then
        echo "> 현재 구동 중인 애플리케이션이 없으므로 종료하지 않습니다."
else
        echo "> kill -15 $CURRENT_PID"
        kill -15 $CURRENT_PID
        sleep 5
fi

echo "> 새 애플리케이션 배포"

JAR_NAME=$(ls -tr $REPOSITORY/ | grep 'springboot-study-project-qrna-0.0.1-SNAPSHOT.jar' | tail -n 1)

echo "> JAR Name: $JAR_NAME"

nohup sudo java -jar \
        -Dspring.config.location=classpath:/application-real.properties,/home/ec2-user/qrna/application-real-db.properties \
        -Dspring.profiles.active=real \
        $JAR_NAME > $REPOSITORY/nohup.out 2>&1 &