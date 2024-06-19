import java.math.BigDecimal;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        List<Funcionario> funcionarios = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        funcionarios.add(new Funcionario("Maria", LocalDate.parse("18/10/2000", formatter), new BigDecimal("2009.44"), "Operador"));
        funcionarios.add(new Funcionario("João", LocalDate.parse("12/05/1990", formatter), new BigDecimal("2284.38"), "Operador"));
        funcionarios.add(new Funcionario("Caio", LocalDate.parse("02/05/1961", formatter), new BigDecimal("9836.14"), "Coordenador"));
        funcionarios.add(new Funcionario("Miguel", LocalDate.parse("14/10/1988", formatter), new BigDecimal("19119.88"), "Diretor"));
        funcionarios.add(new Funcionario("Alice", LocalDate.parse("05/01/1995", formatter), new BigDecimal("2234.68"), "Recepcionista"));
        funcionarios.add(new Funcionario("Heitor", LocalDate.parse("19/11/1999", formatter), new BigDecimal("1582.72"), "Operador"));
        funcionarios.add(new Funcionario("Arthur", LocalDate.parse("31/03/1993", formatter), new BigDecimal("4071.84"), "Contador"));
        funcionarios.add(new Funcionario("Laura", LocalDate.parse("08/07/1994", formatter), new BigDecimal("3017.45"), "Gerente"));
        funcionarios.add(new Funcionario("Heloísa", LocalDate.parse("24/05/2003", formatter), new BigDecimal("1606.85"), "Eletricista"));
        funcionarios.add(new Funcionario("Helena", LocalDate.parse("02/09/1996", formatter), new BigDecimal("2799.93"), "Gerente"));

        funcionarios.removeIf(funcionario -> funcionario.getNome().equals("João"));

        System.out.println("Lista de Funcionários:");
        funcionarios.forEach(funcionario -> {
            String dataNascimentoFormatada = funcionario.getDataNascimento().format(formatter);
            String salarioFormatado = NumberFormat.getNumberInstance(Locale.GERMAN).format(funcionario.getSalario());
            System.out.println("Nome: " + funcionario.getNome() +
                    ", Data de Nascimento: " + dataNascimentoFormatada +
                    ", Salário: " + salarioFormatado +
                    ", Função: " + funcionario.getFuncao());
        });

        funcionarios.forEach(funcionario -> {
            BigDecimal aumento = funcionario.getSalario().multiply(new BigDecimal("0.10"));
            funcionario.setSalario(funcionario.getSalario().add(aumento));
        });

        Map<String, List<Funcionario>> funcionariosPorFuncao = funcionarios.stream()
                .collect(Collectors.groupingBy(Funcionario::getFuncao));

        System.out.println("\nFuncionários agrupados por função:");
        funcionariosPorFuncao.forEach((funcao, listaFuncionarios) -> {
            System.out.println("Função: " + funcao);
            listaFuncionarios.forEach(funcionario -> System.out.println(" - " + funcionario.getNome()));
        });

        System.out.println("\nFuncionários que fazem aniversário em Outubro e Dezembro:");
        funcionarios.stream()
                .filter(funcionario -> funcionario.getDataNascimento().getMonthValue() == 10 ||
                        funcionario.getDataNascimento().getMonthValue() == 12)
                .forEach(funcionario -> System.out.println(funcionario.getNome()));

        Funcionario funcionarioMaisVelho = Collections.min(funcionarios, Comparator.comparing(Funcionario::getDataNascimento));
        long idade = ChronoUnit.YEARS.between(funcionarioMaisVelho.getDataNascimento(), LocalDate.now());
        System.out.println("\nFuncionário com a maior idade:");
        System.out.println("Nome: " + funcionarioMaisVelho.getNome() + ", Idade: " + idade + " anos");

        System.out.println("\nLista de Funcionários em ordem alfabética:");
        funcionarios.stream()
                .sorted(Comparator.comparing(Funcionario::getNome))
                .forEach(funcionario -> System.out.println(funcionario.getNome()));

        BigDecimal totalSalarios = funcionarios.stream()
                .map(Funcionario::getSalario)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        System.out.println("\nTotal dos salários dos funcionários: " + NumberFormat.getNumberInstance(Locale.GERMAN).format(totalSalarios));

        BigDecimal salarioMinimo = new BigDecimal("1212.00");
        System.out.println("\nQuantidade de salários mínimos por funcionário:");
        funcionarios.forEach(funcionario -> {
            BigDecimal quantidadeSalariosMinimos = funcionario.getSalario().divide(salarioMinimo, 2, BigDecimal.ROUND_HALF_UP);
            System.out.println(funcionario.getNome() + ": " + quantidadeSalariosMinimos + " salários mínimos");
        });
    }
}
