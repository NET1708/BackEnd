default:
	@echo "============= Build container ============="
	docker build -t backend .

up:
	@echo "============= Starting api locally ============="
	docker-compose up -d
logs:
	@echo "============= Showing logs ============="
	docker-compose logs -f

down:
	@echo "============= Stopping api locally ============="
	docker-compose down

restart:
	@echo "============= Restarting api locally ============="
	docker-compose down
	@echo "============= Rebuiding jar file ============="
	mvn spring-boot:run
	docker-compose up -d

prune:
	@echo "============= delete prune ============="
	docker system prune -a --volumes