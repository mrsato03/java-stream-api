package br.com.treinaweb.java.streams;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.OptionalDouble;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {

	public static void main(String[] args) {
		
		List<Empregado> empregados = new ArrayList<Empregado>();
		empregados.add(new Empregado(1, "Joao", 2000, "Producao"));
		empregados.add(new Empregado(1, "Marcelo", 5000, "Producao"));
		empregados.add(new Empregado(2, "Maria", 3000, "RH"));
		empregados.add(new Empregado(3, "Jose", 5000, "Controladoria"));
		empregados.add(new Empregado(4, "Josefina", 7000, "CTO"));
		
		System.out.println("** Funcionarios que começam com J: ");
		
		// Lazy loading - uma stream só é executada após o uso de um operador de terminação
		Stream<Empregado> stream = empregados.stream().filter((emp) -> {
			System.out.println(" ** Invocando o filter()");
			return emp.getNome().startsWith("J");
		});
		
		System.out.println(" ** A conversão para lista será invocada");
		List<Empregado> empregadosComJ = stream.collect(Collectors.toList());
		
		// Pipelines
//		List<Empregado> empregadosComJ = empregados.stream()
//				.filter((emp) -> emp.getNome().startsWith("J"))
//				.collect(Collectors.toList());

		empregadosComJ.stream().forEach((emp) -> System.out.println(emp.getNome()));
		
		OptionalDouble menorSalario = empregadosComJ.stream().mapToDouble((emp) -> emp.getSalario()).min();
		if(menorSalario.isPresent()) {
			System.out.println(String.format("Menor salário: R$ %.2f", menorSalario.getAsDouble()));
		}
		
		// Estastisticas com collect()
		DoubleSummaryStatistics sumario = empregados.stream().collect(Collectors.summarizingDouble(Empregado::getSalario));
		System.out.println("Estatísticas dos salários: ");
		System.out.println(String.format("Maior salário: R$ %.2f", sumario.getMax()));
		System.out.println(String.format("Menor salário: R$ %.2f", sumario.getMin()));
		System.out.println(String.format("Salário médio: R$ %.2f", sumario.getAverage()));
		System.out.println(String.format("Folha salarial: R$ %.2f", sumario.getSum()));
		
		// MAP
		System.out.println("** Nome dos empregados: ");
		List<String> nomeEmpregados = empregados.stream().map((emp) -> emp.getNome()).collect(Collectors.toList());
		nomeEmpregados.forEach(System.out::println);
		
		String nomesSeparadosPorVirgula = empregados.stream().map(Empregado::getNome)
				.reduce("Nome dos empregados: ", (n1, n2) -> n1 + ", " + n2);
		System.out.println(nomesSeparadosPorVirgula);
		
		Map<String, List<Empregado>> dadosDepartamento = empregados.stream().filter(emp -> emp.getDepartamento().equals("Producao")).collect(Collectors.groupingBy(Empregado::getDepartamento));
		System.out.println("** Empregados por departamento: ");
		dadosDepartamento.forEach((dep, emps) -> {
			System.out.println(String.format(" - %s, %d funcionários", dep, emps.size()));
			emps.forEach((emp) -> System.out.println(emp.getNome()));
		});
		
		
		// LAMBDA E METHOR REFERENCE
		// LAMBDA - EXTRAI TODAS AS VEZES QUE É EXECUTADO
		empregados.stream().forEach((emp) -> System.out.println(emp.getNome()));
		
		// METHOD - REFERENCE EXTRAI UMA VEZ SÓ
		empregados.stream().forEach(System.out::println);
		PrintStream print = System.out;
		empregados.stream().forEach((nome) -> {
			print.println(nome);
		});
		
//		System.out.println(" ** LISTA DE EMPREGADOS **");
////		for(Empregado emp : empregados) {
////			System.out.println(emp.getNome());
////		}
//		empregados.stream().forEach(emp -> {
//			System.out.println(emp.getNome());
//		});
//		
////		double salarioTotal = 0;
////		for (Empregado emp : empregados) {
////			salarioTotal += emp.getSalario();
////		}
//		double salarioTotal = empregados.stream().mapToDouble(emp -> emp.getSalario()).sum();
//		System.out.println(String.format("Salário total: R$ %.2f", salarioTotal));
//		
//		
//		Mensageiro mensageiro = (mensagem) -> System.out.println("Mensagem da expressão lambda: " + mensagem);
//		mensageiro.emitirMensagem("Henrique");
//		
		
//		//******************************************
//		// TIPOS DE INTERFACES FUNCIONAIS MAIS COMUNS
//		// Consumer
//		// Entra um parametro e não retorna nada
//		System.out.println("Execução do consumer: ");
//		Consumer<Empregado> consumer = (emp) -> System.out.println(String.format("%s , R$ %.2f", emp.getNome(), emp.getSalario()));
//		consumer.accept(new Empregado(10, "TreinaWeb", 1000, "Educacao"));
//		
//		// Functions
//		// Function<Tipo da entrada, Tipo do retorno>
//		System.out.println("Execução da function");
//		Function<Empregado, Double> function = (emp) -> emp.getSalario() * 10;
//		double novoSalario = function.apply(new Empregado(1000, "", 1, ""));
//		System.out.println(novoSalario);
//		
//		// BinaryOperator
//		// Entra dois parametros do mesmo tipo e retorna o mesmo tipo de entrada
//		System.out.println("Execução do BinaryOperator: ");
//		BinaryOperator<Empregado> binaryOperator = (emp1, emp2) -> new Empregado(-1, emp1.getNome()+emp2.getNome(), emp1.getSalario()+emp2.getSalario(), "Junção");
//		Empregado novoEmpregado = binaryOperator.apply(new Empregado(1, "Treina", 1000, ""), new Empregado(2, "Web", 10000, ""));
//		System.out.println(String.format("%s, R$ %.2f", novoEmpregado.getNome(), novoEmpregado.getSalario()));
//		
//		// Predicate
//		// Entra um parametro e retorna um Boolean
//		System.out.println("Execução do Predicate: ");
//		Predicate<Empregado> predicate = (emp) -> emp.getNome().endsWith("Web");
//		Boolean terminaComWeb = predicate.test(new Empregado(0, "TreinaWeb", 0, ""));
//		System.out.println(terminaComWeb);
//		
//		// Supplier
//		// Não recebe parametros e retorna um tipo
//		System.out.println("Execução do Supplier: ");
//		Supplier<Empregado> supplier = () -> new Empregado(new Random().nextInt(), "TreinaWeb", 0, "");
//		Empregado emp1 = supplier.get();
//		System.out.println(emp1.getId());
//		Empregado emp2 = supplier.get();
//		System.out.println(emp2.getId());
		
	}

}
