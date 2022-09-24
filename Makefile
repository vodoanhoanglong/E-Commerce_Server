dev: ## start DB
	docker compose -f docker-compose.yaml up -d

down: ## stop DB
	docker compose -f docker-compose.yaml down