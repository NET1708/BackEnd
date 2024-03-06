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
	docker rmi backend-app
	docker-compose up -d

prune:
	@echo "============= delete prune ============="
	docker system prune -a --volumes -f

network:
	@echo "============= create network ============="
	docker network create swd391-network