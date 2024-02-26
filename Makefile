default:
	@echo "============= Building Local API ============="
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
	docker-compose up -d