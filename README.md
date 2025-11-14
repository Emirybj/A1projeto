# Projeto Final de Desenvolvimento de Aplicativos Móveis: Playlist de Músicas

Projeto finalizado, seguindo o padrão arquitetural MVVM, implementando persistência local de dados (Room) e integração de rede (Retrofit). O aplicativo permite a criação e gerenciamento de playlists com músicas buscadas de uma API externa.

## 1. Membros da Equipe e Contribuições

| Nome | Matrícula | Área Principal de Contribuição |
| :--- | :--- | :--- |
| **Emily Bueno de Jesus** | 38230127 | Arquitetura Base, Setup do Gradle, Definição de Entidades (Room/Relações), Injeção de Dependência e **UI da Tela Principal (Lista/Busca Local)**. |
| **Caio Felipe de Jesus Souza** | 37761714 | **Integração de Rede (Retrofit/API Deezer)**, DTOs (Data Transfer Objects), Lógica de Busca Externa e **UI da Tela de Busca de Músicas (Tela 3)**. |
| **Murilo Hitoshi Kumegawa** | 41291506 | Lógica de Relacionamento N-N, Criação do `DetailsViewModel` e **UI da Tela de Detalhes da Playlist (Tela 2)**. |

---

## 2. Requisitos Funcionais e Arquitetura

O projeto atende integralmente aos requisitos obrigatórios:

### Requisitos Funcionais (CRUD e Busca)

| RF | Funcionalidade | Status |
| :--- | :--- | :--- |
| **CRUD (Criação)** | O usuário pode criar novas playlists (via `TextField` + `Button`). | ✅ Completo |
| **CRUD (Busca)** | A lista principal é pesquisável (BusCA específica) e as playlists salvas podem ser lidas (`LazyColumn`). | ✅ Completo |
| **CRUD (Exclusão)** | O usuário pode excluir playlists inteiras (Tela 1) e músicas individuais (Tela 2). | ✅ Completo |
| **RF05 (Retrofit)** | Busca de músicas (título, artista, capa) é realizada via API externa (Deezer). | ✅ Completo |
| **RF02 (Room)** | Persistência de dados das playlists (Entidades: Playlist, SongCache, PlaylistSongCrossRef) utilizando Room. | ✅ Completo |

### Arquitetura Utilizada

* **Padrão:** **MVVM** (Model-View-ViewModel) com Padrão Repositório.
* **UI:** Jetpack Compose (Single Activity).
* **Bibliotecas Chave:** Room, Retrofit, Kotlin Coroutines (Flow/StateFlow), Navigation Compose, Coil (para imagens).

---

## 3. Detalhamento de Rede e Banco de Dados (Documentação)

### A) Estrutura do Banco de Dados (Room)

O banco de dados utiliza um relacionamento Muitos-para-Muitos (N-N), implementado através da tabela `PlaylistSongCrossRef`.

| Entidade | Chave Primária | Atributos Principais |
| :--- | :--- | :--- |
| **Playlist** | `playlistId` (Long) | `nome` |
| **SongCache** | `songApiId` (String) | `titulo`, `artista`, `urlCapa`, `urlAudio` |
| **PlaylistSongCrossRef** | `playlistId` + `songApiId` | `ordem` (posição da música na playlist) |

### B) Detalhamento do Consumo de API (Retrofit)

* **API Utilizada:** Deezer Public API
* **URL Base:** `https://api.deezer.com/`
* **Endpoint Principal:** `GET /search?q={search_query}`
* **Uso no Projeto:** O `PlaylistRepositoryImpl` chama este endpoint para popular a `SongSearchScreen` (Tela 3).

---

## 4. Instruções para Execução do Projeto

1.  **Pré-requisitos:** Certifique-se de que o **Android Studio** está atualizado e que a **SDK Platform 34 ou 35** está instalada.
2.  **Clonar o Repositório:** Abra seu terminal e clone o projeto:
    ```bash
    git clone [https://aws.amazon.com/pt/what-is/repo/](https://aws.amazon.com/pt/what-is/repo/)
    ```
3.  **Abrir:** Abra a pasta raiz do projeto (`A1projeto`) no Android Studio.
4.  **Sincronização:** O Studio deve sincronizar o Gradle automaticamente. Se houver problemas, vá em **File > Sync Project with Gradle Files**.
5.  **Rodar:** Inicie um Emulador **Pixel (API 34/35)** e clique em `Run 'app'`.

### Fluxo de Teste

1.  **Tela 1:** Digite um nome (ex: "Kpop"). Clique em **Criar**. A playlist aparece.
2.  **Tela 1 -> Tela 2:** Clique no cartão "Kpop".
3.  **Tela 2:** Clique no botão flutuante **`♪`** (Nota Musical).
4.  **Tela 3:** Digite uma busca (ex: "Stray Kids") e aguarde os resultados da API.
5.  **Adicionar:** Clique em **Add** ao lado de uma música. O app volta para a Tela 2.
6.  **Verificação:** A música adicionada deve aparecer na lista da playlist "Kpo".
