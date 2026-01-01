# Pomodoro App - Plano de Desenvolvimento

Este documento descreve o plano de desenvolvimento para um aplicativo Pomodoro usando Jetpack Compose.

## Estrutura Geral

A arquitetura será baseada em duas telas principais (rotas de navegação) e um `ViewModel` para gerenciar o estado globalmente.

-   **`InitialScreen`**: Tela de configuração dos tempos (Pomodoro, Pausa Curta, Pausa Longa) e para iniciar um novo ciclo.
-   **`TimerScreen`**: Tela principal que exibirá o timer e se adaptará a três estados internos:
    -   **Rodando (Running)**
    -   **Pausado (Paused)**
    -   **Finalizado (Finished)**
-   **`PomodoroViewModel`**: Um `ViewModel` que sobreviverá às mudanças de configuração e navegação, mantendo o estado do timer (tempo restante, estado atual, configurações de tempo).

## Telas e Layouts

### 1. `InitialScreen` (Configuração)

-   **Layout**: `Column` centralizada.
-   **Componentes**:
    -   Título: `Text` "Pomodoro".
    -   Configuradores de Tempo: Uma `Row` para cada tipo de tempo (Pomodoro, Pausa Curta, Pausa Longa) contendo:
        -   `Text` com o nome.
        -   `Text` com o valor em minutos.
        -   `IconButtons` (`+` e `-`) para ajuste.
    -   Botão de Ação: `Button` "Começar" para navegar até a `TimerScreen`.

### 2. `TimerScreen` (Timer Principal)

-   **Layout**: `Column` centralizada.
-   **Componente Central**:
    -   Um `Box` para sobrepor o `CircularProgressIndicator` e o contador de tempo.
    -   **`CircularProgressIndicator`**: Animado com `animateFloatAsState`, mostrando o progresso do tempo. Uma versão "trilha" por baixo com progresso total pode ser usada para melhor UX.
    -   **`Text` (Contador)**: Exibe o tempo restante no formato "MM:SS" no centro do círculo.
-   **Componentes de Controle**:
    -   Uso de `AnimatedVisibility` para mostrar os botões de acordo com o estado do timer:
        -   **`Running`**: Botão "Pausar".
        -   **`Paused`**: `Row` com botões "Continuar" e "Parar".
        -   **`Finished`**: Mensagem de finalização e botão para iniciar a pausa ou voltar ao início.

## Lógica e Animações

-   **Contador (Countdown)**: Será implementado dentro de um `LaunchedEffect` na `TimerScreen`. Ele será ativado quando o estado for `Running` e irá atualizar o `ViewModel` a cada segundo.
-   **Animações**:
    -   `animateFloatAsState` para a barra de progresso circular.
    -   `AnimatedVisibility` para transições suaves dos botões de controle.
-   **Navegação**:
    -   Jetpack Navigation com Compose.
    -   Duas rotas: `"initial"` e `"timer"`.
    -   Transições de tela podem ser configuradas com `enterTransition` e `exitTransition` no `NavHost`.
-   **Extras**:
    -   Implementar vibração ou um som (`MediaPlayer`) ao final de um ciclo.
