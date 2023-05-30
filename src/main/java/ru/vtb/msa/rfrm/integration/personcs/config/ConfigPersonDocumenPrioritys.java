package ru.vtb.msa.rfrm.integration.personcs.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.vtb.msa.rfrm.integration.personcs.entity.Priority;

import java.util.Set;
import java.util.TreeSet;

@Configuration
public class ConfigPersonDocumenPrioritys {

    @Bean
    public Set<Priority> documentIdentityPrioritySet() {
        Set<Priority> set = new TreeSet<>();
        set.add(Priority.builder().
                priority(1).cod("21").name("Паспорт РФ").build());
        set.add(Priority.builder()
                .priority(2).cod("14").name("Временное удостоверение личности гражданина РФ").build());
        set.add(Priority.builder()
                .priority(3).cod("4").name("Удостоверение личности военнослужащего (для офицеров, прапорщиков и мичманов)").build());
        set.add(Priority.builder()
                .priority(4).cod("7").name("Военный билет военнослужащего/временное удостоверение, выданное взамен военного билета").build());
        set.add(Priority.builder()
                .priority(5).cod("101").name(
                        "Паспорт иностранного гражданина либо иной документ, установленный федеральным законом или признаваемый в соответствии с международным Договором РФ в качестве документа, удостоверяющего личность иностранного гражданина").build());
        set.add(Priority.builder().priority(6).cod("12").name("Вид на жительство (для лиц без гражданства, если они постоянно проживают на территории Российской Федерации)").build());
        return set;
    }

    @Bean
    public Set<Priority> personAddressSet() {
        Set<Priority> set = new TreeSet<>();
        set.add(Priority
                .builder().priority(1).cod("1").name("Постоянной регистрации").build());
        set.add(Priority
                .builder().priority(2).cod("2").name("Временной регистрации").build());
        set.add(Priority
                .builder().priority(3).cod("3").name("Фактический").build());
        set.add(Priority
                .builder().priority(4).cod("10").name("Почтовый").build());
        return set;
    }

    @Bean
    public Set<Priority> personPhoneSet() {
        Set<Priority> set = new TreeSet<>();
        set.add(Priority
                .builder().priority(1).cod("7").name("Для нотификаций").build());
        set.add(Priority
                .builder().priority(2).cod("6").name("Мобильный личный").build());
        set.add(Priority
                .builder().priority(3).cod("2").name("Контактный").build());
        set.add(Priority
                .builder().priority(4).cod("1").name("Домашний").build());
        set.add(Priority
                .builder().priority(5).cod("3").name("Фактический").build());
        set.add(Priority
                .builder().priority(6).cod("8").name("Телефон по месту постоянной регистрации").build());
        set.add(Priority
                .builder().priority(7).cod("14").name("Телефон по месту временной регистрации").build());
        return set;
    }

    @Bean
    public Set<Priority> personEmailSet() {
        Set<Priority> set = new TreeSet<>();
        set.add(Priority
                .builder().priority(1).cod("18").name("Email БКО СФЛ").build());
        set.add(Priority
                .builder().priority(2).cod("19").name("Email контактный СФЛ").build());
        set.add(Priority
                .builder().priority(3).cod("12").name("Рабочий").build());
        set.add(Priority
                .builder().priority(4).cod("11").name("Личный").build());
        set.add(Priority
                .builder().priority(5).cod("13").name("Для нотификаций").build());
        return set;
    }
}
