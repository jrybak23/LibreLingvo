package org.libre.lingvo.model;

/**
 * Created by igorek2312 on 03.11.16.
 */
public class TranslationsCriteria {
    private ActionOptions actionOption;
    private TranslationSortFieldOptions sortFieldOption;
    private SortingOptions sortingOption;

    public TranslationsCriteria(
            ActionOptions actionOption,
            TranslationSortFieldOptions sortFieldOption,
            SortingOptions sortingOption
    ) {
        this.actionOption = actionOption;
        this.sortFieldOption = sortFieldOption;
        this.sortingOption = sortingOption;
    }

    public ActionOptions getActionOption() {
        return actionOption;
    }

    public void setActionOption(ActionOptions actionOption) {
        this.actionOption = actionOption;
    }

    public TranslationSortFieldOptions getSortFieldOption() {
        return sortFieldOption;
    }

    public void setSortFieldOption(TranslationSortFieldOptions sortFieldOption) {
        this.sortFieldOption = sortFieldOption;
    }

    public SortingOptions getSortingOption() {
        return sortingOption;
    }

    public void setSortingOption(SortingOptions sortingOption) {
        this.sortingOption = sortingOption;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TranslationsCriteria that = (TranslationsCriteria) o;

        if (actionOption != that.actionOption) return false;
        if (sortFieldOption != that.sortFieldOption) return false;
        return sortingOption == that.sortingOption;

    }

    @Override
    public int hashCode() {
        int result = actionOption != null ? actionOption.hashCode() : 0;
        result = 31 * result + (sortFieldOption != null ? sortFieldOption.hashCode() : 0);
        result = 31 * result + (sortingOption != null ? sortingOption.hashCode() : 0);
        return result;
    }
}
