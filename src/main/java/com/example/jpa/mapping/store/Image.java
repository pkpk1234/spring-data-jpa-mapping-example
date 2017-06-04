/*
 * Copyright 2013 the original author or authors.
 *
 * Licensed under the Apache License, MainVersion 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.jpa.mapping.store;

import lombok.NoArgsConstructor;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@NoArgsConstructor
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(updatable = false)
    private String name;

    @Column(unique = true)
    private int index;

    @ManyToOne(fetch = FetchType.LAZY)
    private Product product;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "image", orphanRemoval = true)
    @OrderBy("type")
    private Set<MainVersion> MainVersions = new LinkedHashSet<MainVersion>();

    public Image(String name, int index) {
        this.name = name;
        this.index = index;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIndex() {
        return index;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public Set<MainVersion> getMainVersions() {
        return MainVersions;
    }

    public void addMainVersion(MainVersion MainVersion) {
        MainVersions.add(MainVersion);
        MainVersion.setImage(this);
    }

    public void removeMainVersion(MainVersion MainVersion) {
        MainVersions.remove(MainVersion);
        MainVersion.setImage(null);
    }

    @Override
    public int hashCode() {
        HashCodeBuilder hcb = new HashCodeBuilder();
        hcb.append(name);
        hcb.append(product);
        return hcb.toHashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Image)) {
            return false;
        }
        Image that = (Image) obj;
        EqualsBuilder eb = new EqualsBuilder();
        eb.append(name, that.name);
        eb.append(product, that.product);
        return eb.isEquals();
    }

    @Override
    public String toString() {
        ToStringBuilder tsb = new ToStringBuilder(this);
        tsb.append("id", id);
        tsb.append("name", name);
        tsb.append("index", index);
        tsb.append("product", product);
        return tsb.toString();
    }
}
