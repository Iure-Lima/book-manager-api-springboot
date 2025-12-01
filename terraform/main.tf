terraform {
  required_providers {
    kubernetes = {
      source  = "hashicorp/kubernetes"
      version = "~> 2.27.0"
    }
  }
}

provider "kubernetes" {
  config_path = var.kube_config
}

resource "kubernetes_namespace" "atividade" {
  metadata {
    name = var.namespace
  }
}

# MONGODB
resource "kubernetes_deployment" "mongo" {
  metadata {
    name      = "book-manager-db"
    namespace = kubernetes_namespace.atividade.metadata[0].name
  }

  spec {
    replicas = 1
    selector {
      match_labels = {
        app = "mongodb"
      }
    }
    template {
      metadata {
        labels = {
          app = "mongodb"
        }
      }
      spec {
        container {
          image = "mongo:latest"
          name  = "mongo"
          
          port {
            container_port = 27017
          }

          env {
            name  = "MONGO_INITDB_ROOT_USERNAME"
            value = "admin"
          }
          env {
            name  = "MONGO_INITDB_ROOT_PASSWORD"
            value = "admin123"
          }
          env {
            name  = "MONGO_INITDB_DATABASE"
            value = "book-manager"
          }
        }
      }
    }
  }
}

# Service para o Mongo 
resource "kubernetes_service" "mongo_svc" {
  metadata {
    name      = "mongodb"
    namespace = kubernetes_namespace.atividade.metadata[0].name
  }
  spec {
    selector = {
      app = "mongodb"
    }
    port {
      port        = 27017
      target_port = 27017
    }
    type = "ClusterIP"
  }
}

# BACKEND
resource "kubernetes_deployment" "backend" {
  metadata {
    name      = "backend-app"
    namespace = kubernetes_namespace.atividade.metadata[0].name
  }

  spec {
    replicas = 1
    selector {
      match_labels = {
        app = "backend"
      }
    }
    template {
      metadata {
        labels = {
          app = "backend"
        }
      }
      spec {
        container {
          image = "ghcr.io/Iure-Lima/book-manager-api-springboot:latest" 
          name  = "backend"
          image_pull_policy = "Always"

          port {
            container_port = 8080
          }

          env {
            name  = "PORT"
            value = "8080"
          }
          env {
            name  = "DATABASE"
            value = "book-manager"
          }
          env {
            name  = "JWT_SECRET"
            value = "book-mangar-library-secret"
          }
          
          env {
            name  = "DBURI"
            value = "mongodb://admin:admin123@mongodb:27017/book-manager?authSource=admin"
          }
        }
      }
    }
  }
}

# Service que expoem o Backend
resource "kubernetes_service" "backend_svc" {
  metadata {
    name      = "backend-service"
    namespace = kubernetes_namespace.atividade.metadata[0].name
  }
  spec {
    selector = {
      app = "backend"
    }

    # NodePort permite acessar via IP do host
    type = "NodePort" 
    port {
      port        = 8081      # Porta externa desejada
      target_port = 8080      # Porta da aplicação Java
      node_port   = 30081     # Porta fixa
    }
  }
}
