variable "kube_config" {
  description = "Caminho para o arquivo de config do Kubernetes"
  type        = string
  default     = "~/.kube/config"
}

variable "namespace" {
  description = "Namespace para isolar a atividade"
  default     = "atividade-02"
}
