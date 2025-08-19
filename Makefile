run-dist:
	./app/build/install/app/bin/app
build:
	make -C ./app build
clean:
	make -C ./app clean
run:
	make -C ./app run
test:
	make -C ./app test
installDist:
	make -C ./app installDist